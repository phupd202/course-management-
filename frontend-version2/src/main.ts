// css and font
// import './assets/css/bootstrap-datetimepicker.css'
// import './assets/css/emoji.css'
// import './assets/css/feather.css'
// import './assets/css/lightbox.css'
import './assets/css/style.css'
// import './assets/css/themify-icons.css'
// import './assets/css/video-player.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')
