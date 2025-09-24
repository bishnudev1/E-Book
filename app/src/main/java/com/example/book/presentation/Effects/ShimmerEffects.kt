package com.example.book.presentation.Effects

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerEffects() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()
    val transitionAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = ""
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(transitionAnimation.value, transitionAnimation.value)
    )

    ShimmerGridItem(brush)

}

@Composable
fun ShimmerGridItem(brush: Brush){
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), shape = RoundedCornerShape(8.dp)) {
        Row (
            modifier = Modifier.fillMaxWidth().height(150.dp)
                .padding(all= 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)).background((brush)))
            Spacer(modifier = Modifier.padding(10.dp))
//            Spacer(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)))
            Column(verticalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.padding((5.dp)))
                Spacer(modifier = Modifier.size((20.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.7f)).background((brush)))
                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.3f)).background((brush)))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShimmerGridItemPreview(){
    ShimmerGridItem(brush = Brush.linearGradient(listOf(Color.LightGray,Color.White)))
}
@Composable
fun CategoryShimmer() {
    val shimmerColors = listOf(   Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),)

    val transition = rememberInfiniteTransition()
    val transitionAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = ""
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(transitionAnimation.value, transitionAnimation.value)
    )

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), shape = RoundedCornerShape(8.dp)) {
        Column (
            modifier = Modifier.height(150.dp).size(200.dp)
                .padding(all= 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Spacer(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background((brush)).fillMaxWidth().weight(1f).clip(
                RoundedCornerShape(8.dp)))
            Spacer(modifier = Modifier.padding(10.dp))
//            Spacer(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)))
            Column(verticalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.padding((5.dp)))
                Spacer(modifier = Modifier.size((20.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.7f)).background((brush)))
                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.9f)).background((brush)))

                Spacer(modifier = Modifier.padding(3.dp))
                Spacer(modifier = Modifier.size((16.dp)).clip(RoundedCornerShape(8.dp)).fillMaxWidth((0.3f)).background((brush)))
            }
        }
    }
}

@Composable
fun ImageAGI() {
    val shimmerColors = listOf(   Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),)

    val transition = rememberInfiniteTransition()
    val transitionAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        label = ""
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(transitionAnimation.value, transitionAnimation.value)
    )

    Spacer(modifier = Modifier.size((80.dp)).clip(RoundedCornerShape(8.dp)).background((brush)))
}