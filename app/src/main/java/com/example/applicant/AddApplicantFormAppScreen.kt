package com.example.applicant

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.applicant.util.Recurrence
import com.example.applicant.util.getRecurrenceList
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApplicantFormAppScreen() {
    var applicantName: String by rememberSaveable { mutableStateOf("") }
    var applicantFName: String by rememberSaveable { mutableStateOf("") }
    var applicantSName: String by rememberSaveable { mutableStateOf("") }
    var applicantSchool: String by rememberSaveable { mutableStateOf("") }
    var recurrence by rememberSaveable { mutableStateOf(Recurrence.Medicina.name) }
    var birthDate by rememberSaveable { mutableStateOf(Date().time) }

    Column (
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = stringResource(id = R.string.add_applicant),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.add_applicant_name),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = applicantName,
            onValueChange = { applicantName = it },
            placeholder = { Text(text = "e.g. John") },
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_applicant_firts_second_name),
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = applicantFName,
                onValueChange = { applicantFName = it },
                placeholder = { Text(text = "e.g. Doe") },
            )
        }
        Text(
            text = stringResource(id = R.string.add_applicant_second_second_name),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = applicantSName,
            onValueChange = { applicantSName = it },
            placeholder = { Text(text = "e.g. Blow") },
        )

        Spacer(modifier = Modifier.padding(8.dp))

        BirthDateTextField { birthDate = it }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.add_applicant_school),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = applicantSchool,
            onValueChange = { applicantSchool = it },
            placeholder = { Text(text = "e.g. My School") },
        )

        Spacer(modifier = Modifier.padding(4.dp))

        RecurrenceDropdownMenu { recurrence = it }

        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                validateMedication(
                    name = applicantName,
                    firtsSName = applicantFName,
                    SecondSName = applicantSName,
                    recurrence = recurrence,
                    birthDate = birthDate,
                    school = applicantSchool,
                    onInvalidate = {
                        Log.d("Activity", "invalido "+applicantName)
                    },
                    onValidate = {
                        // TODO: Navigate to next screen / Store medication info
                        Log.d("Activity", "Applicant Added: "+applicantName+" "+
                                                                        applicantFName+" "+
                                                                        applicantSName+"\n"+
                                                    "career: "+recurrence+"\n"+
                                                    "birthDate: "+birthDate+"\n"+
                                                    "School: "+applicantSchool+"\n")
                    }
                )
            },
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurrenceDropdownMenu(recurrence: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recurrence),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getRecurrenceList().map { it.name }
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            recurrence(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateTextField(birthDate: (Long) -> Unit) {
    Text(
        text = stringResource(id = R.string.birth_date),
        style = MaterialTheme.typography.bodyLarge
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    val currentDate = Date().toFormattedString()
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH)
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog =
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            selectedDate = "${month.toMonthName()} $dayOfMonth, $year"
            birthDate(newDate.timeInMillis)
        }, year, month, day)

    TextField(
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        trailingIcon = { Icons.Default.DateRange },
        interactionSource = interactionSource
    )

    if (isPressed) {
        datePickerDialog.show()
    }
}

fun Int.toMonthName(): String {
    return DateFormatSymbols().months[this]
}

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

private fun validateMedication(
    name: String,
    firtsSName: String,
    SecondSName: String,
    recurrence: String,
    birthDate: Long,
    school: String,
    onInvalidate: (Int) -> Unit,
    onValidate: () -> Unit
) {
    if (name.isEmpty()) {
        onInvalidate(R.string.add_applicant_name)
        return
    }

    if (firtsSName.isEmpty()) {
        onInvalidate(R.string.add_applicant_firts_second_name)
        return
    }

    if (SecondSName.isEmpty()) {
        onInvalidate(R.string.add_applicant_second_second_name)
        return
    }

    if (school.isEmpty()) {
        onInvalidate(R.string.add_applicant_school)
        return
    }
    onValidate()
}