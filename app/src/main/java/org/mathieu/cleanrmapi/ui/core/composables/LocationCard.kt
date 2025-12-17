package org.mathieu.cleanrmapi.ui.core.composables
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mathieu.cleanrmapi.domain.models.location.Location

@Composable
fun LocationCard(location: Location){

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Localisation : ${location.name}")
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Type de localisation : ${location.type}")
        Text(text = "Dimension : ${location.dimension}")
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Liste des ${location.residents.size} résidents : ")
        for (character in location.residents) {
            Text(text = "Resident : ${character.name}")
        }
    }
}