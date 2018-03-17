import Vue from 'vue'
import router from './router'
import App from './app.vue'

import ElementUI from 'element-ui'

Vue.use(ElementUI);

export const mainVue = new Vue({
	el: '#app',
	template: "<app/>",
	components: {
		'app': App
	},
	router
}).$mount("#app");

