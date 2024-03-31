// helpers.ts
export function getDayOfWeekName(dayOfWeek: number): string {
    switch (dayOfWeek) {
      case 1:
        return 'Chủ Nhật';
      case 2:
        return 'Thứ 2';
      case 3:
        return 'Thứ 3';
      case 4:
        return 'Thứ 4';
      case 5:
        return 'Thứ 5';
      case 6:
        return 'Thứ 6';
      case 7:
        return 'Thứ 7';
      default:
        return 'Chưa có';
    }
}

export function getStatus(isClosed : boolean): string {
    if(isClosed) {
        return "Đã đóng";
    }
    return "Đang mở";
}

export function isAssigned(beginDate: string | null, endDate: string | null, beginTime: string | null, endTime: string | null, lecturerId: number | null): boolean {
  if (beginDate === null && endDate === null && beginTime === null && endTime === null && lecturerId === null) {
      return false;
  }
  return true;
}


  