package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme

@Composable
fun SharedRadioGroup(
    selectedOption: String,
    radioOptions: List<String>,
    modifier: Modifier = Modifier,
    onOptionSelect: (Int) -> Unit,
) {
    Row(modifier.selectableGroup()) {
        radioOptions.forEachIndexed { index, text ->
            Row(
                Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelect(index) },
                )
                SharedText(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SharedRadioGroupPreview() {
    WeatherAppPortfolioTheme {
        SharedRadioGroup(
            radioOptions = listOf("km/h", "m/h"),
            selectedOption = "km/h",
            onOptionSelect = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SharedRadioGroupPreview2() {
    WeatherAppPortfolioTheme {
        SharedRadioGroup(
            radioOptions = listOf("mm", "inch"),
            selectedOption = "mm",
            onOptionSelect = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SharedRadioGroupPreview3() {
    WeatherAppPortfolioTheme {
        SharedRadioGroup(
            radioOptions = listOf("celsius", "fahrenheit"),
            selectedOption = "fahrenheit",
            onOptionSelect = {}
        )
    }
}
