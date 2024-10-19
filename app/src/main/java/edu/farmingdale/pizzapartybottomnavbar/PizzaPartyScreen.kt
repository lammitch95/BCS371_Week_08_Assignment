package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.PizzaPartyBottomNavBarTheme
import kotlin.math.ceil

// ToDo 6: Add another level of hunger that is Hungry that is in between Medium and Very hungry

// ToDo 7: Using the ViewModel class, create a new ViewModel class called PizzaPartyViewModel as
// a subclass of ViewModel. Add the following properties to the PizzaPartyViewModel - see Brightspace

@Composable
fun PizzaPartyScreen( modifier: Modifier = Modifier,partyViewModel: PizzaPartyViewModel = viewModel()) {

    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            modifier = modifier.padding(bottom = 16.dp)
        )
        NumberField(
            labelText = "Number of people?",
            textInput = partyViewModel.numPeopleInput,
            onValueChange = { partyViewModel.numPeopleInput = it },
            modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
        )

        val selectedOption = when (partyViewModel.hungerLevel) {
            HungerLevel.LIGHT -> "Light"
            HungerLevel.MEDIUM -> "Medium"
            HungerLevel.HUNGRY -> "Hungry"
            HungerLevel.VERY_HUNGRY -> "Very hungry"
        }

        RadioGroup(
            labelText = "How hungry?",
            radioOptions = listOf("Light", "Medium","Hungry", "Very hungry"),
            selectedOption = selectedOption,
            onSelected = {option ->
                partyViewModel.hungerLevel = when (option) {
                    "Light" -> HungerLevel.LIGHT
                    "Medium" -> HungerLevel.MEDIUM
                    "Hungry" -> HungerLevel.HUNGRY
                    "Very hungry" -> HungerLevel.VERY_HUNGRY
                    else -> HungerLevel.MEDIUM 
                }
                         },
            modifier = modifier
        )
        Text(
            text = "Total pizzas: ${partyViewModel.totalPizzas}",
            fontSize = 22.sp,
            modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Button(
            onClick = {
                partyViewModel.calculateNumPizzas()
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column {
        Text(labelText)
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = modifier.padding(end = 8.dp)
                )
                Text(
                    text = option,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}


fun calculateNumPizzas(
    numPeople: Int,
    hungerLevel: String
): Int {
    val slicesPerPizza = 8
    val slicesPerPerson = when (hungerLevel) {
        "Light" -> 2
        "Medium" -> 3
        "Hungry" -> 4
        else -> 5
    }

    return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
}

@Preview(showBackground = true)
@Composable
fun PizzaPartyScreenPreview() {
    PizzaPartyBottomNavBarTheme {
        PizzaPartyScreen()
    }
}
