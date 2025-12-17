package org.mathieu.cleanrmapi.ui.core.composables
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mathieu.cleanrmapi.domain.models.location.LocationPreview

@Composable
fun LocationPreviewCard(location: LocationPreview, onClick: (id: Int) -> Unit){

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick(location.id) }
    ) {
        Text(text = "Localisation : ${location.name}")
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "Type de localisation : ${location.type}")
    }
}