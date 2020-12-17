package com.rufree.dobi.api.service.discord

import com.rufree.dobi.api.exception.DiscordBadCommandException
import org.springframework.stereotype.Service

@Service
class HelpService {

    fun getHelpCommand(keyword: String): String {
        return when(keyword) {
            "일정" -> scheduleCommand
            "" -> ""
            else -> throw DiscordBadCommandException()
        }
    }
}

val scheduleCommand = """
** 1. 일정 및 참석 여부 관리 ** 

1-1. 일정추가
- __ 특정 시간에 스케줄을 예약해주시면 10분전에 알람을 드립니다.😍 __
`[사용법] !일정예약 yyyy-MM-dd HH:mm 일정제목`
`[예시] !일정예약 1994-05-06 00:00 하나봇의 생일`

1-2. 일정보기
- __ 등록 된 일정들에 대해 목록을 보여드립니다. __
`[사용법] !일정보기`

1-3. 참가인원확인
- __ 특정 일정에 참가한 인원들을 확인할 수 있습니다. __
`[사용법] !참가인원확인 [Calendar ID]`

1-4. 일정삭제
- __ 등록하신 일정을 삭제 할 수 있습니다. __
`[사용법] !일정삭제 [Calendar ID]`

1-5. 참가신청
- __ 등록된 일정에 참가 신청을 할 수 있습니다. __
`[사용법] !참가신청 [Calendar ID] [코멘트 (100자 이하)]`

1-6. 참가거절
- __ 등록된 일정에 참여 불가를 신청 할 수 있습니다. __
`[사용법] !참가거절 [Calendar ID] [코멘트 (100자 이하)]`
""".trimIndent()

