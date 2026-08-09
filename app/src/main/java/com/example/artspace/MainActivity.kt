package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Art(
    val img: Int = 1,
    val title: String = "Art title",
    val artist: String = "Artist name",
    val productionYear: Int = 2026
)

val artworkList = listOf<Art>(
    Art(R.drawable.art1, "Still Life of Blue Rose and Other Flowers", "Owen Scott", 2012),
    Art(R.drawable.art2, "Art title 2", "Artist name 2", 2013),
    Art(R.drawable.art3, "Art title 3", "Artist name 3", 2014),
    Art(R.drawable.art4, "Art title 4", "Artist name 4", 2015),
    Art(R.drawable.art5, "Art title 5", "Artist name 5", 2020),
    Art(R.drawable.art6, "Art title 6", "Artist name 6", 2022),
    Art(R.drawable.art7, "Art title 7", "Artist name 7", 2023),
    Art(R.drawable.art8, "Art title 8", "Artist name 8", 2024),
    Art(R.drawable.art9, "Art title 9", "Artist name 9", 2025),
    Art(R.drawable.art10, "Art title 10", "Artist name 10", 2026),
)

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    val maxIdx = artworkList.size - 1
    var artIndex by remember { mutableIntStateOf(0) }
    val artBoxShadow = remember { // 在 dropShadow 中使用 shadow
        Shadow(
            radius = 20.dp, // 增大模糊半径（15-30dp 效果柔和）
            spread = 0.dp, // spread 设为 0，不额外扩散
            color = Color.Black.copy(alpha = 0.2f), // 浅黑色更自然
            offset = DpOffset(x = 0.dp, y = 8.dp) // 轻微向下偏移
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(500.dp)
                    // .border(0.dp, Color(0xFF333333), RoundedCornerShape(12.dp))
                    .dropShadow( // 👈 阴影放在 background 之前
                        shape = RoundedCornerShape(12.dp), // 圆角让阴影更自然
                        shadow = artBoxShadow
                    )
                    .background(Color(0xFFFFFFFF))
            ) {
                AnimatedContent(
                    targetState = artIndex,
                    // transitionSpec = { fadeIn() togetherWith fadeOut() },
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },
                    label = "artwork_transition"
                ) { index ->
                    key(index) {
                        Image(
                            painter = painterResource(artworkList[artIndex].img),
                            contentDescription = null,
                            // contentScale 默认 Fit，若图片本身是横屏（宽 > 高），宽度先撑满，高度留白。若是竖屏则高度先撑满，宽度留白
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(30.dp)
                        )
                    }
                }
            }
        }
        ArtInfoCard(
            art = artworkList[artIndex],
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            prevClickHandler = { artIndex = if (artIndex < 1) maxIdx else artIndex - 1 },
            nextClickHandler = { artIndex = if (artIndex >= maxIdx) 0 else artIndex + 1 },
        )
    }
}

@Composable
private fun ArtInfoCard(
    art: Art,
    modifier: Modifier = Modifier,
    prevClickHandler: () -> Unit,
    nextClickHandler: () -> Unit,
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEAE9F2))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = art.title,
                        modifier = Modifier,
                        fontSize = 24.sp,
                        lineHeight = 30.sp,
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Light,
                        fontFamily = FontFamily.Default
                    )
                    Row {
                        Text(
                            text = art.artist,
                            modifier = Modifier.padding(end = 5.dp),
                            fontSize = 18.sp,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Default
                        )
                        Text(text = "${art.productionYear}")
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.width(150.dp),
                    onClick = prevClickHandler
                ) {
                    Text(text = "Previous")
                }
                Button(
                    modifier = Modifier.width(150.dp),
                    onClick = nextClickHandler
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceLayoutPreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}