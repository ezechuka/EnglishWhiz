package com.javalon.englishwhiz.presentation.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javalon.englishwhiz.R
import com.javalon.englishwhiz.presentation.ui.theme.EnglishWhizTheme

@ExperimentalAnimationApi
@Composable
fun NavItem(item: BottomNavItem, onClick: () -> Unit, selected: Boolean) {

    val backgroundColor =
        if (selected) MaterialTheme.colors.onSurface.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (selected) MaterialTheme.colors.onSurface else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painterResource(id = item.icon),
                tint = contentColor,
                contentDescription = item.title
            )
            Spacer(modifier = Modifier.padding(horizontal = 1.dp))
            AnimatedVisibility(visible = selected) {
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    color = contentColor,
                    fontSize = 13.sp,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun NavItemPreview() {
    EnglishWhizTheme {
        NavItem(
            item = BottomNavItem("Home", R.drawable.home, "home"),
            onClick = { },
            selected = true
        )
    }
}