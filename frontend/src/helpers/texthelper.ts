export function truncateText(text: string, maxLength: number = 50): string {
   if (text.length > maxLength) {
       return text.substring(0, maxLength) + '...';
   } else {
       return text;
   }
}