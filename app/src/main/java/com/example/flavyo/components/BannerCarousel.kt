package com.example.flavyo.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavyo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun BannerCarousel() {
    val bannerImages = listOf(
        R.drawable.strawberry_banner,
        R.drawable.kesar_banner,
        R.drawable.pan_banner
    )
    val pagerState = rememberPagerState(pageCount = { bannerImages.size })

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(durationMillis = 800)
            )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 16.dp
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = bannerImages[page]),
                    contentDescription = "Promo Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.wrapContentHeight().fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFFC1272D) else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}
