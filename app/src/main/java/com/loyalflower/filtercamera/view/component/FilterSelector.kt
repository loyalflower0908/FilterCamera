package com.loyalflower.filtercamera.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.loyalflower.filtercamera.model.filter.FilterType

/**
 * 필터 목록을 가로로 스크롤 가능한 버튼 목록으로 표시하고, 선택된 필터를 처리하는 컴포저블 함수입니다.
 *
 * @param filters 표시할 필터 목록 ([List]<[FilterType]>)
 * @param onFilterSelect 필터 선택 시 호출할 콜백 함수 ([FilterType]을 인자로 받음)
 */
@Composable
fun FilterSelector(filters: List<FilterType>, onFilterSelect: (FilterType) -> Unit) {
    Column {
        LazyRow { // 가로 스크롤 리스트를 생성합니다.
            items(filters) { filter -> // filters 목록의 각 항목에 대해 반복합니다.
                Button(onClick = { onFilterSelect(filter) }) { // 버튼을 생성하고 클릭 시 onFilterSelect 콜백을 호출합니다.
                    Text(filter.name)
                }
                Spacer(modifier = Modifier.width(16.dp)) // 버튼 사이 간격 추가
            }
        }
    }
}