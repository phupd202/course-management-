export function currencyFilter(value: number): string {
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}
  