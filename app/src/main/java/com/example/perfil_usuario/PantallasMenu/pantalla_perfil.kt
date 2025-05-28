package com.example.perfil_usuario.PantallasMenu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.R

@Composable
fun PantallaPerfil(modifier: Modifier = Modifier,
                   name: String,
                   level: Int,
                   team: String,
                   pokedexCount: Int,
                   navController: NavController
                   ){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Profile picture
        Image(
            painter = painterResource(id = R.drawable.avatar_mapa),
            contentDescription = "Trainer Avatar",
            modifier = Modifier
                .size(400.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,

            )

        Spacer(modifier = Modifier.height(16.dp))

        //Player name and level
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(end = 8.dp),
                style = TextStyle(fontSize = 18.sp)
            )
            Text(
                text = "Lv. $level",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 18.sp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Team info
        Text(
            text = "Team: $team",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Player stats
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Player Stats",
                color = Color.Black,
                style = TextStyle(fontSize = 18.sp)
            )
            Modifier.padding(vertical = 8.dp)
            HorizontalDivider(modifier, color = Color.Black)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Pokédex Count:", color = Color.Gray, style = TextStyle(fontSize = 18.sp))
                Text(text = pokedexCount.toString(), color = Color.Black, style = TextStyle(fontSize = 18.sp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = {
                Log.d("NavController", "Attempting to navigate from Perfil. NavController: $navController")
                navController.navigate(PantallaMenuPrincipal.Configuracion.ruta)
                }
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "configuracion")
            Text(text = "Configuracion")
        }
    }
}