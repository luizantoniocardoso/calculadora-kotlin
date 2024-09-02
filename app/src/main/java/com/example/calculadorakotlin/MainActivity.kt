package com.example.calculadorakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorakotlin.ui.theme.CalculadoraKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraKotlinTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                        .padding(16.dp)
                ) {
                    var displayText by remember { mutableStateOf("0") }
                    Text(
                        text = displayText,
                        fontSize = 40.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(Color.Black),
                        textAlign = TextAlign.Right
                    )

                    val buttons = listOf(
                        "7", "8", "9", "/",
                        "4", "5", "6", "*",
                        "1", "2", "3", "-",
                        "0", ".", "=", "+"
                    )

                    var operator by remember { mutableStateOf("") }
                    var operand1 by remember { mutableStateOf("") }

                    buttons.chunked(4).forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            row.forEach { symbol ->
                                Button(
                                    onClick = {
                                        if (symbol in "0".."9" || symbol == ".") {
                                            if (displayText == "0") {
                                                displayText = symbol
                                            } else {
                                                displayText += symbol
                                            }
                                        } else if (symbol == "=") {
                                            val operand2 = displayText.toDoubleOrNull() ?: 0.0
                                            val result = when (operator) {
                                                "+" -> (operand1.toDouble() + operand2).toString()
                                                "-" -> (operand1.toDouble() - operand2).toString()
                                                "*" -> (operand1.toDouble() * operand2).toString()
                                                "/" -> if (operand2 != 0.0) {
                                                    (operand1.toDouble() / operand2).toString()
                                                } else {
                                                    "Error"
                                                }
                                                else -> "0"
                                            }
                                            displayText = result
                                            operator = ""
                                            operand1 = ""
                                        } else {
                                            operator = symbol
                                            operand1 = displayText
                                            displayText = ""
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                ) {
                                    Text(text = symbol, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalculadoraKotlinTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            Text(
                text = "0",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Black),
                textAlign = TextAlign.Right
            )
        }
    }
}
