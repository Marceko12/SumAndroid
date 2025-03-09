package com.nicolas.mytemporizador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolas.mytemporizador.ui.theme.MyTemporizadorTheme
import kotlinx.coroutines.delay
import com.nicolas.mytemporizador.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTemporizadorTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceBright) {
                    CronometroComponent()


                }
            }
        }
    }
}

@Composable
fun CronometroComponent() {
    var tiempoTranscurrido by remember { mutableStateOf(20) }
    var isActivo by remember { mutableStateOf(false) }
    var texto = "Terminado!"

    // Lógica del temporizador
    LaunchedEffect(key1 = isActivo) {
        while (isActivo && tiempoTranscurrido > 0) {
            delay(1000L)
            tiempoTranscurrido--
        }
        isActivo = false
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    )
    {
        // titulo
        Text(
            text = "Temporizador",
            fontSize = 48.sp,  // Tamaño grande
            fontWeight = FontWeight.Bold,  // Texto en negrita
            modifier = Modifier.padding(top = 30.dp),  // Espacio superior
            color = MaterialTheme.colorScheme.primary  // Color del tema
        )

        // imagen de reloj
        Image(
            painter = painterResource(id = R.drawable.clock),
            contentDescription = "Clock",
            modifier = Modifier.size(120.dp)

        )
        // Display del tiempo
        Text(
            text = if (tiempoTranscurrido > 0) formatTime(tiempoTranscurrido) else texto,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (tiempoTranscurrido == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
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
                Image(
                    painter = painterResource(id = if (isActivo) R.drawable.stop else R.drawable.iniciar),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isActivo) "Detener" else "Iniciar")
            }

            Button(
                onClick = {
                    isActivo = false
                    tiempoTranscurrido = 20
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reiniciar),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
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
