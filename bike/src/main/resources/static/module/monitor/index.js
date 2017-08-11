(function(angular){
	'use strict';
	define(['app','BMap','resources/components/js/GPSUtils.js'], function (app, BMap) {
		app.register.controller('MonitorController', ['$rootScope', '$scope', '$resource', '$filter', '$timeout', '$window', '$location', '$interval', '$sce', function($rootScope, $scope, $resource, $filter, $timeout, $window, $location, $interval, $sce){
			var SuperMapResource = $resource('authority/getFeatures', {}, {
				getPoints:{method: 'POST', isArray: true, url: "reporter/location/locations"},
				sendMessage : {method: 'POST',responseType: "json",url:"api/information/vehicle/textMessage"},
				call : {method: 'POST',responseType: "json",url:"api/information/vehicle/call"},
				monitor : {method: 'POST',responseType: "json",url:"api/information/vehicle/monitor"},
				getOnlineCount : {method: 'get',responseType: "json",url:"api/information/vehicle/onlineCount"},
				getRealTimeTreeData : {method : 'GET', url: "api/map/realtime/tree"},
				getBorrowRecord : { method : 'GET', isArray: true, url : 'reporter/integrated/borrow/bike/:bikeId/borrowRecord'},
				getAddress : {method: 'get',responseType: "json",url:"api/information/vehicle/getAddress/:point"},
				getVehicleDetails : {method: 'POST',responseType: "json",url:"api/map/realtime/vehicleDetails"},
				getParkDetail : {method: 'GET',responseType: "json",url:"api/information/park/:parkId"},
				getCountTime : {method: 'GET',responseType: "json",url:"reporter/integrated/borrow/bike/:bikeId/countTime"}
			});
			var historyTrackPlayer, historyMap;
			var styleOptions = {
		        strokeColor:"blue",
		        fillColor:"blue",
		        strokeWeight: 3,
		        strokeOpacity: 0.8,
		        fillOpacity: 0.6,
		        strokeStyle: 'solid',
		        enableEditing: false,
		        enableClicking: true
		    };
			var map, ztree, setting = {
//	    		async: { enable: true, url:"vehicle/list", autoParam:["id"], type: "get"},
				view: { dblClickExpand: dblClickExpand },
				check: { enable: true, chkDisabledInherit: true },
				data: { simpleData: { enable: true } },
				callback: {
					onCheck: function(event, treeId, node){
						var nodes = [];
						var points = [];
						if(node.isParent){
							nodes = ztree.getNodesByFilter(function(n){
								return n.center !== undefined;
							}, false, node);
						}else{
							nodes[0] = node;
						}
						node.checked ? (function(){
							for(var i = 0; i < nodes.length; i++){
								var vector = map.addVector(nodes[i]);
								vector.addEventListener("click",function(e){
									var geoc = new BMap.Geocoder();  
									var pt = e.point;
									SuperMapResource.getVehicleDetails(e.currentTarget.data,function(response){
										let target = response;
										$scope.dialog.target = target;
										geoc.getLocation(pt, function(rs){
											$scope.$apply(function(){
												$scope.dialog.target.address = rs.address;
											});
										});
										SuperMapResource.getCountTime({"bikeId":target.id},function(response){
											$scope.dialog.target.countTime = response.countTime;
										});
										var aObj = $("#nav-tabs-project").children(":first").children(":first");
										aObj.click();
										$scope.dialog.show();
									});
								});
								if(nodes.length == 1){
//									map.panTo(vector.getPosition());
									map.setViewport([vector.getPosition()]);
								}else{
									points.push(vector.point);
								}
							}
							if(node.level == 2 && node.isParent){
								SuperMapResource.getParkDetail({"parkId":node.id},function(response){
									addPark(response);
								});
								if (points.length > 1) {
									map.setViewport(points);
								} else {
									map.setViewport([point]);
								}
							}
							if (node.level == 1) {
								var childs = node.children;
								for(var i = 0; i < childs.length; i++) {
									if (childs[i].isParent) {
										SuperMapResource.getParkDetail({"parkId":childs[i].id},function(response){
											addPark(response);
										});
									}
								}
								if (points.length > 1) {
									map.setViewport(points);
								} else {
									map.setViewport([point]);
								}
							}
							if (node.level == 3) {
								var parentNode = node.getParentNode();
								SuperMapResource.getParkDetail({"parkId":parentNode.id},function(response){
									addPark(response);
								});
							}
//							if(node.isParent && points.length > 1){
//								map.setViewport(points);
//							}
						})(): removeOldOverlay(nodes, node);//map.removeVector(nodes);
					},
					beforeCheck: function(){},
					onAsyncSuccess: function(){}
				}
			};
		    
			map = new BMap.Map("map",{enableMapClick:false});
			map.enableScrollWheelZoom();
			map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));
			map.addControl(new BMap.OverviewMapControl({"isOpen":false}));
			map.centerAndZoom(new BMap.Point(113.64964385, 34.75661006), 15);
			map.enableAutoResize();
			function addMarker(point){
				var marker = new BMap.Marker(point);
				map.addOverlay(marker);
			}
			
			function dblClickExpand(treeId, treeNode) {
				return treeNode.level > 0;
			}
			
			function addPark(park) {
				var bdpt = GPSUtils.fromWGS84ToBD09(park.lat, park.lng);
				var point = new BMap.Point(bdpt.lng, bdpt.lat);
				var marker = new BMap.Marker(point);
				marker.id = park.id;
				map.addOverlay(marker);
				var circle = new BMap.Circle(point, park.radius, styleOptions);
				circle.id = park.id;
				map.addOverlay(circle);
			}
			
			function removePark(node) {
				var overlays = map.getOverlays();
				var vectors = overlays.map(function(e){return e.id});
				var i = vectors.indexOf(node.id);
				if (i !== -1 && vectors.length > (i+1) && vectors[i] == vectors[i+1]) {
					map.removeOverlay(overlays[i]);
					map.removeOverlay(overlays[i+1]);
				}
			}
			
			function removeOldOverlay(nodes, node) {
				map.removeVector(nodes);
				if (node.isParent && node.level == 2) {
					removePark(node);
				} else if (node.level == 3) {
					var parentNode = node.getParentNode();
					if (!parentNode.checked) {
						removePark(parentNode);
					}
				} else if (node.level == 1) {
					var childs = node.children;
					for(var i = 0; i < childs.length; i++) {
						if (childs[i].isParent) {
							removePark(childs[i]);
						}
					}
				}
			}
			
			$scope.$on('dialog-completed', function(e, dialog, element){
				historyMap = new BMap.Map("historyMap",{enableMapClick:false});
				historyMap.enableScrollWheelZoom();
				historyMap.addControl(new BMap.NavigationControl());
				historyMap.centerAndZoom(new BMap.Point(113.64964385, 34.75661006), 16);
				historyMap.enableAutoResize();
				
				dialog.start = {
					"time" : (function(){
						var now = new Date();
						now.setHours(0,0,0,0);
						return now;
					})(),
					"open": function($event) {
						dialog.start.opened = true;
					}
				};
				dialog.end = {
					"open": function($event) {
						dialog.end.opened = true;
					},
					"maxDate":new Date(),
					"time":new Date()
				};
				dialog.tabEvent2 = function(event){
					historyMap.clearOverlays();
				};
				dialog.tabEvent3 = function(event){
					var target = $scope.dialog.target;
					SuperMapResource.getBorrowRecord({"bikeId":target.id},function(response){
						dialog.borrowRecordList = response;
					});
				};
				dialog.history = function(item){
					dialog.borrowItem = item;
					angular.element('#nav-tabs-project li:eq(1) a').tab('show');
					historyMap.clearOverlays();
					dialog.play();
				};
				dialog.play = function(){
					if(historyTrackPlayer && historyTrackPlayer.getStatus()){
						historyTrackPlayer.start();
						return;
					}
					if(historyTrackPlayer){
						historyMap.clearOverlays();
						historyTrackPlayer.stop();
						historyTrackPlayer = undefined;
					}
					var data = {
						"vehicle": dialog.borrowItem.vehicle,
						"startTime": dialog.borrowItem.startTime,
						"endTime": dialog.borrowItem.endTime
					};
					SuperMapResource.getPoints(data, function(response){
						delete response.$promise;
						delete response.$resolved;
						historyMap.clearOverlays();
						historyMap.setViewport(response);
						var routerLine = new BMap.Polyline((function(array){
							var points = [];
							angular.forEach(array, function(item) {
								this.push(new BMap.Point(item.lng, item.lat));
							}, points);
							return points;
						})(response), {strokeColor:"green",fillColor:"green",strokeWeight: 3,
										strokeOpacity: 0.8,fillOpacity: 0.6,strokeStyle: 'solid',
										enableEditing: false,enableClicking: true});
						historyMap.addOverlay(routerLine);
						historyTrackPlayer = new BMapLib.HistoryTrackPlayer(historyMap, response);
						historyTrackPlayer.start();
					});
				};
				dialog.stop = function(){
					historyTrackPlayer.stop();
				};
				dialog.pause = function(){
					historyTrackPlayer.pause();
				};
				
				/*dialog.ctrl1 = function(){
//					if ($scope.dialog.target.info.state.value == 'OFFLINE') {
//						$scope.$confrim.init({title:'警告',type:'warning',content : '车辆未上线,无法进行视频监控!'}).show();
//					} else {
						$scope.$confrim.init({title:'警告',type:'warning',content : '打开实时视频将耗费较多的SIM卡流量，您确定要打开吗?',callback:function(){
							console.log($scope);
							$scope.vedio.target = $scope.dialog.target;
							$scope.vedio.channels = [];
							for(let i = 0; i < 6; i++){
								$scope.vedio.channels.push($sce.trustAsResourceUrl("http://www.youtube.com/embed/zpOULjyy-n8?rel=" + i));	
							}
							$scope.vedio.show();
						}}).show();
//					}
				};
				dialog.ctrl2 = function(){
					$scope.$confrim.init({title:'警告',type:'warning',content : '暂不支持此功能!'}).show();
//					$scope.$confrim.init({title:'警告',type:'warning',content : '远程断电对车辆的安全行驶产生严重影响，您确定要断电吗?',callback:function(){
//						console.log('远程断电');
//					}}).show();
				};
				dialog.ctrl3 = {
					"state": false,
					"open": function(){
						dialog.ctrl3.state = true;
					},
					"close": function(){
						dialog.ctrl3.state = false;
						dialog.ctrl3.message = undefined;
					},
					
					"submit": function(){
						console.log({"simNo": dialog.target.terminal.simNo, "messageInfo": dialog.ctrl3.message});
						$scope.$confrim.init({title:'信息',type:'info',content : '已请求向终端发送文本信息,请等待响应!'}).show();
						$timeout(function(){
							$scope.$confrim.close();
							$(".md-overlay").css("z-index","1000");
						}, 2000);
						var data = {
								"simNo": dialog.target.terminal.simNo,
								"messageInfo": dialog.ctrl3.message
							};
						//调用发送短信接口
						SuperMapResource.sendMessage(data,function(response){
							commandResponse(response.id,3);
//							dialog.ctrl3.close();
						});
					}
				};
				dialog.ctrl4 = {
					"state": false,
					"open": function(){
						dialog.ctrl4.state = true;
						dialog.ctrl4.message = angular.copy($scope.operator.mobile);
					},
					"close": function(){
						dialog.ctrl4.state = false;
						dialog.ctrl4.message = undefined;
					},
					"submit": function(){
						console.log({"id": dialog.target.id, "message": dialog.ctrl4.message});
						$scope.$confrim.init({title:'信息',type:'info',content : '已向终端请求语音通话,请等待响应!'}).show();
						$timeout(function(){
							$scope.$confrim.close();
							$(".md-overlay").css("z-index","1000");
						}, 2000);
						var data = {
								"simNo": dialog.target.terminal.simNo,
								"telNo": dialog.ctrl4.message
							};
						//调用语音通话接口
						SuperMapResource.call(data,function(response){
							if(response.id==null){
								$scope.$confrim.init({title:'错误',type:'danger',content : '电话号码格式错误!'}).show();
								return;
							}
							commandResponse(response.id,4);
//							dialog.ctrl4.close();
						});
					}
				};
				dialog.ctrl5 = {
					"state": false,
					"open": function(){
						dialog.ctrl5.state = true;
						dialog.ctrl5.message = angular.copy($scope.operator.mobile);
					},
					"close": function(){
						dialog.ctrl5.state = false;
						dialog.ctrl5.message = undefined;
					},
					"submit": function(){
						console.log({"id": dialog.target.id, "message": dialog.ctrl5.message});
						$scope.$confrim.init({title:'信息',type:'info',content : '已向终端请求语音监听,请等待响应!'}).show();
						$timeout(function(){
							$scope.$confrim.close();
							$(".md-overlay").css("z-index","1000");
						}, 2000);
						var data = {
								"simNo": dialog.target.terminal.simNo,
								"telNo": dialog.ctrl5.message
							};
						//调用车辆监听接口
						SuperMapResource.monitor(data,function(response){
							if(response.id==null){
								$scope.$confrim.init({title:'错误',type:'danger',content : '电话号码格式错误!'}).show();
								return;
							}
							commandResponse(response.id,5);
//							dialog.ctrl5.close();
						});
						
					}
				};*/
				$scope.$on('dialog-closed', function(e, dialog, element){
					historyMap.clearOverlays();
					if(historyTrackPlayer !== undefined){
						historyTrackPlayer.stop();
						historyTrackPlayer = undefined;
					}
				});
			});
			
//			$scope.$on('vedio-completed', function(e, dialog, element){
//				angular.element(".col-md-4", element).height((window.innerHeight - 97)/2);
//				flowplayer("#hlsjslive1", {
//				    splash: false,
//				    embed: false,
//				    ratio: 9/16,
//
//				    clip: {
//				      hlsQualities: [
//				        { level: 0, label: "标清" },
//				        { level: 1, label: "高清" },
//				        { level: 2, label: "超清" }
//				      ],
//				      hlsjs: {
//				        smoothSwitching: false
//				      },
//				      live: true,
//				      sources: [
//				        { type: "application/x-mpegurl",
//				          src: "http://nasatv-lh.akamaihd.net/i/NASA_101@319270/master.m3u8" }
//				      ]
//				    }
//
//				  });
//				flowplayer("#hlsjslive4", {
//				    splash: false,
//				    embed: false,
//				    ratio: 9/16,
//
//				    clip: {
//				      hlsQualities: [
//				        { level: 0, label: "标清" },
//				        { level: 1, label: "高清" },
//				        { level: 2, label: "超清" }
//				      ],
//				      hlsjs: {
//				        smoothSwitching: false
//				      },
//				      live: true,
//				      sources: [
//				        { type: "application/x-mpegurl",
//				          src: "http://192.168.241.217:9126/lskjhlsapp/2100001201002592/index.m3u8" }
//				      ]
//				    }
//
//				  });
//				$scope.$on('vedio-closed', function(e, dialog, element){
//					dialog.channels = [];
//				});
//			});
			
			$scope.leftMenu = {
				state : true,
				switching: function(){
					$scope.leftMenu.state = !$scope.leftMenu.state;
				}
			};
			
			$scope.treenodes = SuperMapResource.getRealTimeTreeData();
			$scope.treenodes.$promise && $scope.treenodes.$promise.then(function(response){
				delete response.$promise;
				delete response.$resolved;
				ztree = $.fn.zTree.init($("#ztree"), setting, response);
			    ztree.expandNode(ztree.getNodes()[0], true, false, false);
			});
			
			
			//获取上下线车辆
			$scope.vehicle = SuperMapResource.getOnlineCount();
			
			var subscribe = function(){
				$rootScope.stompClient.subscribe('/topic/status', function(response) {
					$scope.$apply(function(){
						var vector = JSON.parse(response.body);
						map.resetVector(vector);
						var znode = ztree.getNodesByParam("id", vector.id);
						//如果推送的数据为树形结构中的车辆对象时，更新相应的结点
						if(znode !== undefined && znode.length > 0 && znode[0] !== undefined){
							znode = znode[0];
							znode.info = vector.info;
							znode.center = vector.center;
							/*if(typeof(vector.info.state)!=undefined){
								znode.iconSkin = vector.info.state.value.toLowerCase();
							}else{
								znode.iconSkin = "offline";
							}*/
							ztree.updateNode(znode);
						}
						//如果推送的数据为对话框中显示的数据时
						if($scope.dialog.target!==undefined && vector.id === $scope.dialog.target.id){
							$scope.dialog.target.info = vector.info;
							$scope.dialog.target.center = vector.center;
						}
						//获取上下线车辆
						$scope.vehicle = SuperMapResource.getOnlineCount();
					});
				});
			};
			//实时指令下发响应
			var commandResponse = function(id,i){
				var subscription = $rootScope.stompClient.subscribe('/topic/response/'+id, function(response) {
					$scope.$apply(function(){
						if(response){
							$scope.$confrim.init({title:'成功',type:'success',content : '指令发送成功, 车载机大约一分钟会进行应答!'}).show();
							$timeout(function(){
								$scope.$confrim.close();
								$(".md-overlay").css("z-index","1000");
							}, 60000);
							if(i==3){
								$scope.dialog.ctrl3.close();
							}else if (i==4){
								$scope.dialog.ctrl4.close();
							}else if (i==5){
								$scope.dialog.ctrl5.close();
							}
						}else{
							$scope.$confrim.init({title:'错误',type:'danger',content : '指令发送失败!'}).show();
						}
					});
					subscription.unsubscribe();
				});
			};
			
			//车辆上下线
			var onOff = function(){
				$rootScope.stompClient.subscribe('/topic/onoff', function(response) {
					$scope.$apply(function(){
						var onOff1 = JSON.parse(response.body);
						var vector = onOff1.vehicle;
						var znode = ztree.getNodesByParam("id", vector.id);
						//获取地图中所有的覆盖物
						var allOverlay = map.getOverlays();
						var vectors = allOverlay.map(function(e){return e.id});
						var i = vectors.indexOf(vector.id);
						if(znode[0].center!=null && i !== -1 && allOverlay[i] !== undefined){
							vector.center = znode[0].center;
							map.removeVector(vector);
							 var vector1 = map.addVector(vector);
							 vector1.addEventListener("click",function(e){
								 var geoc = new BMap.Geocoder();  
								var pt = e.point;
								SuperMapResource.getVehicleDetails(e.currentTarget.data,function(response){
									let target = response;
									$scope.dialog.target = target;
									geoc.getLocation(pt, function(rs){
										var addComp = rs.addressComponents;
										$scope.$apply(function(){
											$scope.dialog.target.address = addComp.province+ addComp.city + addComp.district + addComp.street + addComp.streetNumber;
										});
									});
									var aObj = $("#nav-tabs-project").children(":first").children(":first");
									aObj.click();
									$scope.dialog.show();
								});
							});
							if(!vector1.isParent){
								map.panTo(vector1.getPosition());
							}
						}
						//如果推送的数据为树形结构中的车辆对象时，更新相应的结点
						if(znode !== undefined && znode.length > 0 && znode[0] !== undefined){
							znode = znode[0];
							if(typeof(onOff1.state)!= undefined){
								znode.iconSkin = onOff1.state.value.toLowerCase();
								znode.info.state= vector.info.state;
							}else{
								znode.iconSkin = "offline";
							}
							ztree.updateNode(znode);
						}
						//如果推送的数据为对话框中显示的数据时
						if($scope.dialog.target!==undefined && vector.id === $scope.dialog.target.id){
							$scope.dialog.target.info.state.index = vector.info.state.index;
						}
						//获取上下线车辆
						$scope.vehicle = SuperMapResource.getOnlineCount();
					});
				});
			};
			$rootScope.stompClient.connected? subscribe():$scope.$on('stomp-completed', subscribe);
			$rootScope.stompClient.connected? onOff():$scope.$on('stomp-completed', onOff);
		}]);
	});
})(window.angular);