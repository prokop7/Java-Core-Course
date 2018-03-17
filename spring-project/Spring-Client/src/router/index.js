import Vue from 'vue'
import Router from 'vue-router'

const Login = {template: '<span>You need to login</span>'};

Vue.use(Router);

export default new Router({
	routes: [
		{ path: '/404', component:
			{template: '<div><p>Page not found</p></div>'}},
	],
	mode: 'history'
})