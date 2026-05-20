data class AttendanceRequest(
    val event_id: Int,
    val member_id: Int,
    val attendance_date: String,
    val status: String,
    val notes: String?
)

data class ApiResponse(
    val status: String,
    val message: String
)
