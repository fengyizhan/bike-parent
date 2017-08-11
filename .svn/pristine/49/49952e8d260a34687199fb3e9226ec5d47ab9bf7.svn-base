(function(angular) {
	'use strict';
	define(['app'], function (app) {
		app.register.controller('DriverController', ["$rootScope", "$scope", "$resource", "$timeout", "$q", function($rootScope, $scope, $resource, $timeout, $q){
			/**============================================接口对象定义部分============================================**/
			var Driver = $resource("api/information/driver/:username", { "username" : "@username" }, {
				init: { method: 'GET', url: 'api/information/driver/init'},
				get: { method: 'GET'},
				updateState: { method: 'GET', url: 'api/information/driver/state/:username'},
				getWalletMessage : { method: 'GET', url: 'wallet/information/walletUser/:username'}
			});
			var Wallet = $resource("wallet/information/walletUser/:username", { "username" : "@username" }, {
				init: { method: 'GET', url: 'wallet/information/recharge/init'}
			});
			/**============================================表单查询定义部分============================================**/
			var selected = {name : "全部"};
			$scope.searchForm = {
				states:[selected],
				data:{
					"state": selected
				},
				submit: function(){
					$scope.searchForm.data = angular.copy($scope.searchForm.data);
				}
			};
			/**============================================页面方法定义部分============================================**/
			$scope.info = function(target) {
				$scope.dialog.editabled = false;
				$scope.dialog.increased = false;
				$scope.dialog.title = target.username + "钱包信息";
				Driver.getWalletMessage({"username":target.username},function(response){
					$scope.dialog.data = response;
				});
				$scope.dialog.show();
			};
			$scope.join = function(target) {
				var content = "";
				if (target.state.index == 0) {
					content = "是否要将" + target.username + "加入黑名单?";
				} else {
					content = "是否要将" + target.username + "移出黑名单?";
				}
				$scope.$confrim.init({title:'警告',type:'warning',content : content,callback:function(){
					Driver.updateState({"username":target.username},function(response){
						$scope.pagination.refresh(response);
                    });
				}}).show();
			};
			
			/**============================================页面元素初始化部分============================================**/
			Driver.init(function(response){
				$scope.searchForm.states = $scope.searchForm.states.concat(response.state);
			});
			/**============================================对话框元素初始化部分============================================**/			
			$scope.$on('dialog-completed', function(e, dialog, element){
				
			});
			/**============================================用户充值信息对话框内元素初始化部分============================================**/
			$scope.$on('userRechargeDialog-completed', function(e, dialog, element){
				dialog.walletSearchForm = {data: undefined};
				$scope.rechargeRecords = function(target){
					dialog.title = target.username+" 充值记录";
					var selected = {name : "全部"};
					$scope.userRechargeDialog.walletSearchForm = {
						styles:[selected],
						data:{
							"username" : target.username,
							"style" : selected,
							"startTime" : $rootScope.start.time,
							"endTime" : $rootScope.end.time
						},
						submit: function(){
							$scope.userRechargeDialog.walletSearchForm.data = angular.copy($scope.userRechargeDialog.walletSearchForm.data);
						}
					};
					Wallet.init(function(response){
						$scope.userRechargeDialog.walletSearchForm.styles = $scope.userRechargeDialog.walletSearchForm.styles.concat(response.styles);
					});
					$scope.userRechargeDialog.walletSearchForm.startTime = {
						"open": function($event) {
							dialog.walletSearchForm.startTime.opened = true;
						}
					};
					$scope.userRechargeDialog.walletSearchForm.endTime = {
						"open": function($event) {
							dialog.walletSearchForm.endTime.opened = true;
						}
					};
					$scope.userRechargeDialog.show();
				};
			});
			/**============================================用户消费信息对话框内元素初始化部分============================================**/
			$scope.$on('userConsumeDialog-completed', function(e, dialog, element){
				dialog.walletSearchForm = {data: undefined};
				$scope.consumeRecords = function(target){
					dialog.title = target.username+" 消费记录";
					$scope.userConsumeDialog.walletSearchForm = {
						data:{
							"username" : target.username,
							"startTime" : $rootScope.start.time,
							"endTime" : $rootScope.end.time
						},
						submit: function(){
							$scope.userConsumeDialog.walletSearchForm.data = angular.copy($scope.userConsumeDialog.walletSearchForm.data);
						}
					};
					$scope.userConsumeDialog.walletSearchForm.startTime = {
						"open": function($event) {
							dialog.walletSearchForm.startTime.opened = true;
						}
					};
					$scope.userConsumeDialog.walletSearchForm.endTime = {
						"open": function($event) {
							dialog.walletSearchForm.endTime.opened = true;
						}
					};
					$scope.userConsumeDialog.show();
				};
			});
		}]);
	});
})(window.angular);