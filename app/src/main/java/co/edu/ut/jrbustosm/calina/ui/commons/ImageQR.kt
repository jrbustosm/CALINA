package co.edu.ut.jrbustosm.calina.ui.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun ImageQR(text: String){

    val bitmap = generateQRBitmap(text)

    Image(
        bitmap = bitmap,
        contentDescription = "",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

fun generateQRBitmap(text: String, size: Int = 512): ImageBitmap {
    val hints = hashMapOf(
        EncodeHintType.MARGIN to 1,
        EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.Q,
        EncodeHintType.CHARACTER_SET to "UTF-8",
    )
    return BarcodeEncoder().encodeBitmap(
        text,
        BarcodeFormat.QR_CODE,
        size,
        size,
        hints
    ).asImageBitmap()
}