import { createApp } from 'vue'
import App from './App.vue'

// Import Bootstrap styles
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';

// Font awesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faHatWizard } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// Jquery
import 'jquery';
import 'popper.js';
import router from './router'
import store from './store/store';

library.add(faHatWizard)
const app = createApp(App)

app.use(store)
app.use(router)

// Register FontAwesomeIcon as a global component
app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
