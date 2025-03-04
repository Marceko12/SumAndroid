package com.nicolas.mytemporizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.mytemporizador.ui.theme.MyTemporizadorTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTemporizadorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CronometroComponent()
                }
            }
        }
    }
}

@Composable
fun CronometroComponent() {
    var tiempoTranscurrido by remember { mutableStateOf(0) }
    var isActivo by remember { mutableStateOf(false) }

    // Lógica del temporizador
    LaunchedEffect(key1 = isActivo) {
        while (isActivo) {
            delay(1000)
            tiempoTranscurrido++
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display del tiempo
        Text(
            text = formatTime(tiempoTranscurrido),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Controles
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                onClick = { isActivo = !isActivo },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isActivo) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (isActivo) "Detener" else "Iniciar")
            }

            Button(
                onClick = {
                    isActivo = false
                    tiempoTranscurrido = 0
                }
            ) {
                Text("Reiniciar")
            }
        }
    }
}

// Función para formatear el tiempo en HH:MM:SS
fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

@Preview(showBackground = true)
@Composable
fun PreviewCronometro() {
    MyTemporizadorTheme {
        CronometroComponent()
    }
}