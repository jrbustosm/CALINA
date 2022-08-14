package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.edu.ut.jrbustosm.calina.MainActivity
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.ui.commons.ImageQR
import co.edu.ut.jrbustosm.calina.ui.commons.generateQRBitmap
import java.io.ByteArrayOutputStream
import java.io.OutputStream

@Composable
fun DialogQR(text: String, title: String, close: ()->Unit){

    Dialog(onDismissRequest = close) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h6
                )
                ImageQR(text = text)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            val context = MainActivity.appContext
                            val intent = Intent(Intent.ACTION_SEND).setType("image/*")
                            val bitmap = generateQRBitmap(text).asAndroidBitmap()
                            val bytes = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                            val shareIntent: Intent? = try{
                                val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    saveImageInQ(bitmap, context)
                                } else {
                                    @Suppress("DEPRECATION")
                                    val path = MediaStore.Images.Media.insertImage(
                                        context.contentResolver,
                                        bitmap,
                                        "LAST_CALINA.jpg",
                                        null
                                    )
                                    Uri.parse(path)
                                }
                                intent.putExtra(Intent.EXTRA_STREAM, uri)
                                Intent.createChooser(intent, null)
                            }catch (e: Exception){
                                null
                            }
                            if(shareIntent!=null) context.startActivity(shareIntent)
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            Icon(imageVector = Icons.Filled.Share, contentDescription = "")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = stringResource(id = R.string.share))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedButton(
                        onClick = close,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(horizontalArrangement = Arrangement.Start) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = stringResource(id = R.string.ok))
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun saveImageInQ(bitmap: Bitmap, context: Context):Uri {
    val filename = "LAST_CALINA.jpg"
    var fos: OutputStream?
    var imageUri: Uri?
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Video.Media.IS_PENDING, 1)
    }

    val contentResolver = context.contentResolver

    contentResolver.also { resolver ->
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }
    }

    fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }

    contentValues.clear()
    contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
    contentResolver.update(imageUri!!, contentValues, null, null)

    return imageUri!!
}