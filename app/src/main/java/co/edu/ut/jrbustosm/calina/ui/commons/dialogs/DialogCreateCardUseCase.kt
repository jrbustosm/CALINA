package co.edu.ut.jrbustosm.calina.ui.commons.dialogs

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.getResource
import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import co.edu.ut.jrbustosm.calina.domain.cardcreator.CardDialogCreate
import co.edu.ut.jrbustosm.calina.domain.cardcreator.DialogFragment
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.util.*
import kotlin.math.abs

@ExperimentalFoundationApi
@Composable
fun DialogCreateCardUseCase(
    appViewModel: AppViewModel,
    useCase: CardDialogCreate,
    cardUIState: CardUIState? = null,
    close: () -> Unit
) {

    val myColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        focusedLabelColor = MaterialTheme.colors.onPrimary,
        cursorColor = MaterialTheme.colors.onPrimary,
        placeholderColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        textColor = MaterialTheme.colors.onPrimary
    )

    val switchColors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colors.onPrimary,
        uncheckedThumbColor = MaterialTheme.colors.primary,
        checkedTrackColor = MaterialTheme.colors.onPrimary,
        uncheckedTrackColor = MaterialTheme.colors.primary,
        checkedTrackAlpha = 0.6f,
        uncheckedTrackAlpha = 0.6f
    )

    var number by remember { mutableStateOf(cardUIState?.number ?: 0) }
    var title by remember { mutableStateOf(cardUIState?.title ?: "") }
    var description by remember { mutableStateOf(cardUIState?.description ?: "") }
    var initialAmount by remember { mutableStateOf(cardUIState?.cash ?: 0) }
    var expanded by remember { mutableStateOf(false) }
    var cashSymbol by remember { mutableStateOf(cardUIState?.cash_symbol ?: '$') }
    var showSelectImage by remember { mutableStateOf(false)}
    var showAdvanceOptions by remember { mutableStateOf(false)}
    var image by remember { mutableStateOf(cardUIState?.imageResource ?: "symbol1")}
    var difficulty by remember { mutableStateOf(cardUIState?.difficulty?.id ?: 'C') }
    var isTransfer by remember { mutableStateOf(cardUIState?.isTransfer ?: true)}
    var isEdit by remember { mutableStateOf(cardUIState?.isEdit ?: false)}
    var isCloneable by remember { mutableStateOf(cardUIState?.isCloneable ?: true)}
    var isDeletable by remember { mutableStateOf(cardUIState?.isDeletable ?: true)}
    var scope by remember { mutableStateOf<String?>(cardUIState?.scope)}
    var isGlobal by remember { mutableStateOf(if(cardUIState==null) false else cardUIState.scope==null)}
    var trigger by remember { mutableStateOf(cardUIState?.trigger ?: "")}
    var dateExpire by remember { mutableStateOf<Date?>(cardUIState?.date_expire)}
    var url by remember { mutableStateOf(cardUIState?.url ?: "")}

    //-----------------------------------------------------
    // Add image select dialog (Basic)
    //-----------------------------------------------------
    if(showSelectImage) DialogSelectImage(
        imageSel = image,
        close = {showSelectImage=false},
        select = {
            image = it
        }
    )

    //-----------------------------------------------------
    // Add Advance options dialog (Basic)
    //-----------------------------------------------------
    if(showAdvanceOptions) DialogAdvanceOptions(
        close = {showAdvanceOptions=false},
        changeDateExpired = { dateExpire = it },
        dateExpired = dateExpire,
        changeTrigger = { trigger = it },
        trigger = trigger,
        changeURL = { url = it},
        url = url,
        myColors = myColors,
        switchColors = switchColors,
        useCase = useCase
    )

    Dialog(onDismissRequest = close) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .background(MaterialTheme.colors.background.copy(alpha = 0.9f))
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(5.dp)
            )
            .verticalScroll(scrollState)
            .padding(20.dp)
        ) {
            val titlePage =
                if(useCase(DialogFragment.GROUP) || useCase(DialogFragment.SECONDARY_GROUP))
                    stringResource(id = R.string.group_card)
                else if(useCase(DialogFragment.ACTION))
                    stringResource(R.string.action_card)
                else if(useCase(DialogFragment.REWARD))
                    stringResource(R.string.reward_card)
                else stringResource(R.string.information_card)
            Text(
                text = titlePage,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6.copy(textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ){
                //-------------------------------------------------------
                // Add number of card textfield (Basic)
                //-------------------------------------------------------
                OutlinedTextField(
                    value = number.toString(),
                    onValueChange = {
                        val t = abs(it.toIntOrNull()?:0)
                        number = if(t>9999) 9999 else t
                    },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.number)) },
                    placeholder = { Text(text = stringResource(R.string.number_of_groupcard)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = myColors,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.width(15.dp))
                //-------------------------------------------------------
                // Add image select field (Basic)
                //-------------------------------------------------------
                Image(
                    painter = painterResource(id = LocalContext.current.getResource(image)),
                    contentDescription = "",
                    modifier = Modifier
                        .weight(1f)
                        .size(60.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colors.primary,
                            RoundedCornerShape(5.dp)
                        )
                        .clickable {
                            showSelectImage = true
                        },
                    contentScale = ContentScale.Fit,
                    colorFilter = if(image.startsWith("symbol")) ColorFilter.tint(MaterialTheme.colors.onPrimary)
                        else null,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            //-------------------------------------------------------
            // Add title of card textfield (Basic)
            //-------------------------------------------------------
            OutlinedTextField(
                value = title,
                onValueChange = { if (it.length <= 40) title = it },
                singleLine = true,
                label = { Text(text = stringResource(R.string.title) + " (${title.length}/40)") },
                placeholder = { Text(text = stringResource(R.string.titleofcard)) },
                colors = myColors,
            )
            Spacer(modifier = Modifier.height(10.dp))
            //-------------------------------------------------------
            // Add description of card textfield (Basic)
            //-------------------------------------------------------
            OutlinedTextField(
                value = description,
                onValueChange = {
                    if (it.length <= 280) description = it
                },
                singleLine = false,
                label = { Text(text = stringResource(R.string.description) + " (${description.length}/280)") },
                placeholder = { Text(text = stringResource(R.string.descriptionofcard)) },
                colors = myColors,
                modifier = Modifier.height(130.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            if(useCase(DialogFragment.GROUP)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    //-------------------------------------------------------
                    // Add cash symbol select (GROUP)
                    //-------------------------------------------------------
                    Text(
                        text = "${stringResource(R.string.cash_symbol)}:",
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = cashSymbol.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable(onClick = { expanded = true })
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 5.dp),
                        textAlign = TextAlign.Center
                    )
                    val items = listOf(
                        '$', '€', '₾', '₺', '₴', '£', '₡', '₲', '֏', '₼', '₪', '₵', '₦', '₣', '৳',
                        '៛', '¥', '元', '₹', '₸', '₭', '₮', '₩', '₱', '؋', '฿', '₫', '₿', 'Ξ', 'Ł',
                        '₽', '¤', '₥', '₳', '₰', '₻', '₯', '₠', '₤', '₶', '₷',
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .background(MaterialTheme.colors.primary),
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                onClick = {
                                    cashSymbol = items[index]
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth(),

                                ) {
                                Text(
                                    text = s.toString(),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onPrimary,
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (s == cashSymbol) MaterialTheme.colors.secondary
                                            else MaterialTheme.colors.primary
                                        )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                //-------------------------------------------------------
                // Add cash amount textfield (GROUP)
                //-------------------------------------------------------
                OutlinedTextField(
                    value = initialAmount.toString(),
                    onValueChange = { initialAmount = it.toIntOrNull() ?: 0 },
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.initial_amount)) },
                    placeholder = { Text(text = stringResource(R.string.initial_amount_group)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = myColors,
                )
            }
            if(!useCase(DialogFragment.GROUP)){
                //-------------------------------------------------------
                // Add difficulty of card select (NON GROUP)
                //-------------------------------------------------------
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${stringResource(R.string.difficulty_toget)}:",
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = DifficultyCardCALINA.getDifficulty(difficulty).toText(),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clickable(onClick = { expanded = true })
                            .background(MaterialTheme.colors.primary)
                            .padding(vertical = 5.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary
                    )
                    val items = listOf(
                        DifficultyCardCALINA.VERY_EASY,
                        DifficultyCardCALINA.EASY,
                        DifficultyCardCALINA.NORMAL,
                        DifficultyCardCALINA.HARD,
                        DifficultyCardCALINA.VERY_HARD
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .background(MaterialTheme.colors.primary),
                    ) {
                        items.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                onClick = {
                                    difficulty = items[index].id
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = s.toText(),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colors.onPrimary,
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (s.id == difficulty) MaterialTheme.colors.secondary
                                            else MaterialTheme.colors.primary
                                        )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.Start) {
                        //-------------------------------------------------------
                        // Add Tranfer switch (NON GROUP)
                        //-------------------------------------------------------
                        Switch(
                            checked = isTransfer,
                            onCheckedChange = { isTransfer = it },
                            colors = switchColors
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(R.string.is_giftable),
                            color = if(isTransfer) MaterialTheme.colors.onPrimary
                            else MaterialTheme.colors.primary,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End) {
                        //-------------------------------------------------------
                        // Add Cloneable switch (NON GROUP)
                        //-------------------------------------------------------
                        Text(
                            text = stringResource(R.string.is_cloneable),
                            color = if(isCloneable) MaterialTheme.colors.onPrimary
                            else MaterialTheme.colors.primary,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Switch(
                            checked = isCloneable,
                            onCheckedChange = { isCloneable = it },
                            colors = switchColors
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.Start) {
                        //-------------------------------------------------------
                        // Add Scope switch (NON GROUP)
                        //-------------------------------------------------------
                        Switch(
                            checked = isGlobal,
                            onCheckedChange = { isGlobal = it },
                            colors = switchColors
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(R.string.is_global),
                            color = if(isGlobal) MaterialTheme.colors.onPrimary
                            else MaterialTheme.colors.primary,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End) {
                        //-------------------------------------------------------
                        // Add Deleteable switch (INFORMATION)
                        //-------------------------------------------------------
                        Text(
                            text = stringResource(R.string.isDeleteable),
                            color = if(isDeletable) MaterialTheme.colors.onPrimary
                            else MaterialTheme.colors.primary,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Switch(
                            checked = isDeletable,
                            onCheckedChange = { isDeletable = it },
                            colors = switchColors
                        )
                    }
                }
                if(useCase(DialogFragment.INFORMATION)){
                    Row(horizontalArrangement = Arrangement.Start) {
                        //-------------------------------------------------------
                        // Add Edit switch (INFORMATION)
                        //-------------------------------------------------------
                        Text(
                            text = stringResource(R.string.is_editable),
                            color = if(isEdit) MaterialTheme.colors.onPrimary
                            else MaterialTheme.colors.primary,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.caption
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Switch(
                            checked = isEdit,
                            onCheckedChange = { isEdit = it },
                            colors = switchColors
                        )
                    }
                }
            }
            //-------------------------------------------------------
            // Advanced Options Button
            //-------------------------------------------------------
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { showAdvanceOptions = true }
            ) {
                Text(text = "${stringResource(R.string.advanced_options)}...")
            }
            //-------------------------------------------------------
            // Add Buttons
            //-------------------------------------------------------
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = close) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                OutlinedButton(
                    enabled = title!="" && description!="",
                    onClick = {
                        scope = if(isGlobal) null else appViewModel.appUIState.currentGroup?.imei_card
                        val type: TypeCardCALINA
                        if(useCase(DialogFragment.GROUP)){
                            type = TypeCardCALINA.GROUP
                            isTransfer = false
                            isCloneable = true
                        }
                        else if(useCase(DialogFragment.SECONDARY_GROUP)) type = TypeCardCALINA.GROUP
                        else if(useCase(DialogFragment.ACTION)) type = TypeCardCALINA.ACTION
                        else if(useCase(DialogFragment.REWARD)) type = TypeCardCALINA.REWARD
                        else type = TypeCardCALINA.INFORMATION

                        val card = CardUIState(
                            title = title,
                            number = number,
                            description = description,
                            cash_symbol = cashSymbol,
                            cash = initialAmount,
                            isTransfer = isTransfer,
                            isEdit = isEdit,
                            isCloneable = isCloneable,
                            scope = scope,
                            trigger = trigger,
                            date_expire = dateExpire,
                            difficulty = DifficultyCardCALINA.getDifficulty(difficulty),
                            type = type,
                            imageResource = image,
                            isSecondary = useCase(DialogFragment.SECONDARY_GROUP),
                            imei_card = cardUIState?.imei_card ?: GenIMEI()(),
                            isSelect = false,
                            //TODO: falta
                            level = "",
                            //TODO: falta
                            levels = "",
                            date_create = Date(),
                            state = StateCardCALINA.NORMAL,
                            count = 0,
                            imei_owner = appViewModel.appUIState.my_imei,
                            imei_maker = appViewModel.appUIState.my_imei,
                            isSymbol = false,
                            date_reg = null,
                            url = url,
                            lang = appViewModel.appUIState.language,
                            isDeletable = isDeletable
                        )
                        if(cardUIState==null)
                            appViewModel.insert(card)
                        else {
                            appViewModel.update(card)
                            if(card.type==TypeCardCALINA.GROUP && !card.isSecondary)
                                appViewModel.updateGroupSelect(card)
                        }
                        close()
                    }
                ) {
                    Text(text =
                        if(cardUIState==null) stringResource(R.string.create)
                        else stringResource(R.string.save)
                    )
                }
            }
        }
    }
}