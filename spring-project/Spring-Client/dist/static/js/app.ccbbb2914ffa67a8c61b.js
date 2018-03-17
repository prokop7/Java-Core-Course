webpackJsonp([1],{

/***/ 148:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 158:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;return _c('div',{attrs:{"id":"app"}},[_c('el-container',[_c('el-header',[(!_vm.token)?_c('el-button',{on:{"click":function($event){_vm.dialogFormVisible = true}}},[_vm._v("Login/Register")]):_vm._e(),_vm._v(" "),(!!_vm.token)?_c('el-button',{on:{"click":function($event){_vm.logout()}}},[_vm._v("Logout")]):_vm._e()],1),_vm._v(" "),_c('el-container',[_c('el-main',[(_vm.number && _vm.token)?_c('p',[_vm._v("Your random number: "+_vm._s(_vm.number))]):_vm._e(),_vm._v(" "),(_vm.number && _vm.token)?_c('el-button',{attrs:{"type":"success"},on:{"click":_vm.getNumber}},[_vm._v("Generate")]):_vm._e(),_vm._v(" "),(_vm.error)?_c('p',[_vm._v(_vm._s(_vm.error))]):_vm._e(),_vm._v(" "),(!_vm.token)?_c('p',[_vm._v("You need to authenticate")]):_vm._e()],1)],1)],1),_vm._v(" "),_c('el-dialog',{attrs:{"title":"Fill in forms","visible":_vm.dialogFormVisible},on:{"update:visible":function($event){_vm.dialogFormVisible=$event}}},[(_vm.errorMessage)?_c('p',[_vm._v(_vm._s(_vm.errorMessage))]):_vm._e(),_vm._v(" "),_c('el-form',{attrs:{"model":_vm.form}},[_c('el-form-item',{attrs:{"label":"Login","label-width":_vm.formLabelWidth}},[_c('el-input',{attrs:{"auto-complete":"off"},model:{value:(_vm.form.login),callback:function ($$v) {_vm.$set(_vm.form, "login", $$v)},expression:"form.login"}})],1),_vm._v(" "),_c('el-form-item',{attrs:{"label":"Password","label-width":_vm.formLabelWidth}},[_c('el-input',{attrs:{"type":"password","auto-complete":"off"},model:{value:(_vm.form.password),callback:function ($$v) {_vm.$set(_vm.form, "password", $$v)},expression:"form.password"}})],1)],1),_vm._v(" "),_c('span',{staticClass:"dialog-footer",attrs:{"slot":"footer"},slot:"footer"},[_c('el-button',{on:{"click":function($event){_vm.dialogFormVisible = false}}},[_vm._v("Cancel")]),_vm._v(" "),_c('el-button',{attrs:{"type":"primary"},on:{"click":function($event){_vm.login()}}},[_vm._v("Login")]),_vm._v(" "),_c('el-button',{attrs:{"type":"primary"},on:{"click":function($event){_vm.register()}}},[_vm._v("Register")])],1)],1)],1)}
var staticRenderFns = []
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);

/***/ }),

/***/ 45:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__api__ = __webpack_require__(93);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



/* harmony default export */ __webpack_exports__["a"] = ({
    components: {},
    data: function () {
        return {
            number: undefined,
            error: "",
            errorMessage: "",
            dialogFormVisible: false,
            token: "",
            form: {
                login: "",
                password: ""
            },
            formLabelWidth: '120px'
        };
    },
    methods: {
        getNumber() {
            let _this = this;

            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].getContent(data => {
                _this.number = data;
                _this.error = "";
            }, (status, e) => {
                _this.number = undefined;
                _this.error = e.message;
                _this.deleteToken();
            });
        },
        deleteToken() {
            console.log("Delete token");
            this.token = "";
            localStorage.removeItem('token');
            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].setToken(null);
        },
        setToken(token) {
            console.log(token);
            this.token = token;
            localStorage.setItem("token", token);
            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].setToken(token);
        },
        logout() {
            let _this = this;
            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].logout(() => _this.deleteToken());
        },
        login() {
            let _this = this;
            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].login(this.form.login, this.form.password, data => {
                _this.setToken(data.token);
                _this.dialogFormVisible = false;
                _this.errorMessage = "";
                _this.getNumber();
            }, (status, e) => {
                _this.errorMessage = e.message;
                console.log(e);
            });
        },
        register() {
            let _this = this;
            __WEBPACK_IMPORTED_MODULE_0__api__["a" /* default */].register(this.form.login, this.form.password, () => _this.login(), (status, e) => {
                _this.errorMessage = e.message;
                console.log(e);
            });
        }
    },
    mounted: function () {
        this.setToken(localStorage.getItem('token'));
        if (this.token) this.getNumber();
    }
});

/***/ }),

/***/ 66:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue_router__ = __webpack_require__(159);



const Login = { template: '<span>You need to login</span>' };

__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]);

/* harmony default export */ __webpack_exports__["a"] = (new __WEBPACK_IMPORTED_MODULE_1_vue_router__["a" /* default */]({
	routes: [{ path: '/404', component: { template: '<div><p>Page not found</p></div>' } }],
	mode: 'history'
}));

/***/ }),

/***/ 68:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_app_vue__ = __webpack_require__(45);
/* unused harmony namespace reexport */
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_0962c3a0_hasScoped_false_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_app_vue__ = __webpack_require__(158);
function injectStyle (ssrContext) {
  __webpack_require__(148)
}
var normalizeComponent = __webpack_require__(157)
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = null
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_app_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_0962c3a0_hasScoped_false_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_app_vue__["a" /* default */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 92:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (immutable) */ __webpack_exports__["a"] = request;
// Default implementation of AJAX request
function request(type, url, data, successCallback, errorCallback, token) {
	var xhr = new XMLHttpRequest();
	xhr.open(type, url, true);
	xhr.setRequestHeader('Accept', 'application/json');
	xhr.setRequestHeader('Content-Type', 'application/json');
	if (token) {
		xhr.setRequestHeader('Token', token);
	}

	xhr.onload = function () {
		if (xhr.status >= 200 && xhr.status < 400) {
			console.log({ type: type, url: xhr.responseURL, response: xhr.response });
			if (xhr.response) {
				// console.log(xhr.status);
				var result;
				try {
					result = xhr.response;
				} catch (e) {
					result = xhr.response.result;
				}
				if (successCallback) {
					successCallback(result);
				}
			} else {
				if (successCallback) {
					successCallback({});
				}
			}
		} else {
			// console.error(xhr.response);
			if (errorCallback) errorCallback(xhr.status, xhr.response);
		}
	};
	xhr.onerror = function () {
		// console.error(xhr);
		if (errorCallback) errorCallback(xhr);
	};
	xhr.responseType = 'json';
	xhr.send(JSON.stringify(data));
}

/***/ }),

/***/ 93:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__ajax__ = __webpack_require__(92);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__config__ = __webpack_require__(94);



let apiToken = "";

function setToken(token) {
    apiToken = token;
}

function getContent(successCallback, errorCallback) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__ajax__["a" /* request */])("GET", `${__WEBPACK_IMPORTED_MODULE_1__config__["a" /* serverUri */]}/content`, {}, successCallback, errorCallback, apiToken);
}

function logout(successCallback, errorCallback) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__ajax__["a" /* request */])("POST", `${__WEBPACK_IMPORTED_MODULE_1__config__["a" /* serverUri */]}/logout`, { token: apiToken }, successCallback, errorCallback, apiToken);
}

function login(login, password, successCallback, errorCallback) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__ajax__["a" /* request */])("GET", `${__WEBPACK_IMPORTED_MODULE_1__config__["a" /* serverUri */]}/login?login=${encodeURIComponent(login)}&password=${encodeURIComponent(password)}`, {}, successCallback, errorCallback, apiToken);
}

function register(login, password, successCallback, errorCallback) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__ajax__["a" /* request */])("POST", `${__WEBPACK_IMPORTED_MODULE_1__config__["a" /* serverUri */]}/register?login=${encodeURIComponent(login)}&password=${encodeURIComponent(password)}`, {}, successCallback, errorCallback, apiToken);
}

/* harmony default export */ __webpack_exports__["a"] = ({
    setToken,
    logout,
    getContent,
    register,
    login
});

/***/ }),

/***/ 94:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
const serverUri = 'http://localhost:8080';
/* harmony export (immutable) */ __webpack_exports__["a"] = serverUri;

/* unused harmony default export */ var _unused_webpack_default_export = ({});

/***/ }),

/***/ 95:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_vue__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__router__ = __webpack_require__(66);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_vue__ = __webpack_require__(68);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_element_ui__ = __webpack_require__(67);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_element_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_element_ui__);






__WEBPACK_IMPORTED_MODULE_0_vue__["default"].use(__WEBPACK_IMPORTED_MODULE_3_element_ui___default.a);

const mainVue = new __WEBPACK_IMPORTED_MODULE_0_vue__["default"]({
	el: '#app',
	template: "<app/>",
	components: {
		'app': __WEBPACK_IMPORTED_MODULE_2__app_vue__["a" /* default */]
	},
	router: __WEBPACK_IMPORTED_MODULE_1__router__["a" /* default */]
}).$mount("#app");
/* harmony export (immutable) */ __webpack_exports__["mainVue"] = mainVue;


/***/ })

},[95]);