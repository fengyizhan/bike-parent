(function(angular){
	'use strict';
	define(['app','echart','BMap'], function (app, echarts, BMap) {
		app.register.controller('IndexController', ['$rootScope', '$scope', '$resource', '$filter', '$timeout', '$window', '$location', '$interval', '$sce', function($rootScope, $scope, $resource, $filter, $timeout, $window, $location, $interval, $sce){
			/**============================================EChart数据初始化部分============================================**/
			var IndexResource = $resource('', {}, {
				getCounts : { method : 'GET', responseType: "json",isArray: false, url: "reporter/information/vehicle/counts"},
				getCountsByWeek: {method : 'GET', responseType: "json",isArray: false, url: "reporter/information/vehicle/countsByWeek"},
				projectInit : { method : 'GET',responseType: "json", url : 'reporter/information/project/info'},
				projectDetail : { method : 'GET',responseType: "json", url : 'reporter/information/project/detail/:id'},
				monitorDetail : { method : 'GET',responseType: "json", url : 'reporter/information/project/monitor/:id'}
			});
			var monitorMap, routeStyle = {
		        strokeColor:"#6A7B8C",
		        fillColor:"#58656F",
		        strokeWeight: 3,
		        strokeOpacity: 0.8,
		        fillOpacity: 0.6,
		        strokeStyle: 'solid',
		        enableEditing: false,
		        enableClicking: true
		    },outsetStyle = {
		        strokeColor:"blue",
		        fillColor:"blue",
		        strokeWeight: 3,
		        strokeOpacity: 0.8,
		        fillOpacity: 0.6,
		        strokeStyle: 'solid',
		        enableEditing: false,
		        enableClicking: true
		    },destinationStyle = {
		        strokeColor:"#6633CC",
		        fillColor:"#6633CC",
		        strokeWeight: 3,
		        strokeOpacity: 0.8,
		        fillOpacity: 0.6,
		        strokeStyle: 'solid',
		        enableEditing: false,
		        enableClicking: true
		    };
			var projectId;
			var vehicleIds;
			//获取昨日数据
//			$scope.countsMap =IndexResource.getCounts();
			//电量统计柱状图
			var option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	"top": -4,
			    	"right": '80',
			    	"itemWidth": 12,
			    	"itemHeight": 12,
			        "textStyle":{
			        	"color": '#ddd'
			        },
				    "data":['当前电量统计']
			    },
			    calculable : true,
			    grid: {
			    	top: 12,
			        left: 0,
			        right: 64,
			        bottom: 10,
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            "axisLine": {
			            	"lineStyle": {
			            		"color": "#ddd"
			            	}
			            },
			            "axisLabel": {
			            	"textStyle": {
			            		"color": "#ddd"
			            	}
			            },
			            "splitLine":{
			            	"show": false
			            },
			            data : ['0-10','11-20','21-30','31-40','41-50','51-60','61-70','71-80','81-90','91-100'],
			            name : "电量%",
			            nameGap : 5
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            "axisLine": {
			            	"lineStyle": {
			            		"color": "#ddd"
			            	}
			            },
			            "axisLabel": {
			            	"textStyle": {
			            		"color": "#ddd"
			            	}
			            },
			            "splitLine":{
			            	"show": false
			            },
			            "splitArea":{
			            	"show": true,
			            	"areaStyle":{
			            		"color": ['rgba(250,250,250,0)','rgba(200,200,200,0.1)']
			            	}
			            }
			        }
			    ],
			    series : [
			        {
			            name:'当前电量统计',
			            type:'bar',
			            data:[340, 450, 720, 530, 890, 910, 280, 660, 167, 82],
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ],
			                "lineStyle": {
			                    "normal": {
			                        "color": "#ddd"
			                    }
			                }
			            },
			            "itemStyle": {
		                    "normal": {
		                        "color": "#4ad0c8"
		                    }
		                }
			        }
			    ]
			};
			var chart1 = echarts.init(document.getElementById('1478855913889'));
			chart1.setOption(option);
//			IndexResource.getCountsByWeek(function(response){
//				var dateList = response.dateList;
//				var vehicleTripsList = response.vehicleTripsList;
//				var onlineCountList = response.onlineCountList;
//				//一周统计，柱状图
//				var option = {
//				    tooltip : {
//				        trigger: 'axis'
//				    },
//				    legend: {
//				    	"top": 0,
//				    	"right": '80',
//				    	"itemWidth": 12,
//				    	"itemHeight": 12,
//				        "textStyle":{
//				        	"color": '#fff'
//				        },
//					    "data":['运输总趟次','车辆上线数']
//				    },
//				    /*toolbox: {
//				    	show:false,
//				    	feature: {
//				    		 mark : {show: true},
//				    		 dataView : {show: true, readOnly: false},
//				    		 magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
//				    		 restore : {show: true}, 
//				    		 saveAsImage : {show: true}
//				    	}
//				    },*/
//				    calculable : true,
//				    grid: {
//				    	top: 24,
//				        left: 0,
//				        right: 64,
//				        bottom: 10,
//				        containLabel: true
//				    },
//				    xAxis : [
//				        {
//				            type : 'category',
//				            "axisLine": {
//				            	"lineStyle": {
//				            		"color": "#fff"
//				            	}
//				            },
//				            "axisLabel": {
//				            	"textStyle": {
//				            		"color": "#fff"
//				            	}
//				            },
//				            "splitLine":{
//				            	"show": false
//				            },
//				            data : dateList
//				        }
//				    ],
//				    yAxis : [
//				        {
//				            type : 'value',
//				            "axisLine": {
//				            	"lineStyle": {
//				            		"color": "#fff"
//				            	}
//				            },
//				            "axisLabel": {
//				            	"textStyle": {
//				            		"color": "#fff"
//				            	}
//				            },
//				            "splitLine":{
//				            	"show": false
//				            },
//				            "splitArea":{
//				            	"show": true,
//				            	"areaStyle":{
//				            		"color": ['rgba(250,250,250,0)','rgba(200,200,200,0.1)']
//				            	}
//				            }
//				        }
//				    ],
//				    series : [
//				        {
//				            name:'运输总趟次',
//				            type:'bar',
//				            data:vehicleTripsList,
//				            markLine : {
//				                data : [
//				                    {type : 'average', name: '平均值'}
//				                ],
//				                "lineStyle": {
//				                    "normal": {
//				                        "color": "#fff"
//				                    }
//				                }
//				            },
//				            "itemStyle": {
//			                    "normal": {
//			                        "color": "#f34e15"
//			                    }
//			                }
//				        },
//				        {
//				            name:'车辆上线数',
//				            type:'bar',
//				            data:onlineCountList,
//				            markLine : {
//				                data : [
//				                    {type : 'average', name: '平均值'}
//				                ],
//				                "lineStyle": {
//				                    "normal": {
//				                        "color": "#ddd"
//				                    }
//				                }
//				            },
//				            "itemStyle": {
//			                    "normal": {
//			                        "color": "#4ad0c8"
//			                    }
//			                }
//				        }
//				    ]
//				};
//				var chart1 = echarts.init(document.getElementById('1478855913889'));
//				chart1.setOption(option);
//			});
			/**============================================工程展示数据初始化部分============================================**/
			/*IndexResource.projectInit(function(response){
				var projectMessage = response.ongoingList;
				// 正在进行的工程数
				$scope.ongoingTotal = response.ongoingTotal;
				// 工程累计总数
				$scope.projectTotal = response.total;
				// 工程信息列表
				$scope.projectList = projectMessage;
				var listSize = projectMessage.length;
				if ((listSize > 0) && (listSize % 3 != 0)) {
					$scope.project_left_heigth = Math.ceil(Number(projectMessage.length/3)) * 186 - 6;
					var completion = 3 - listSize % 3;
					for (var i=0; i<completion; i++) {
						addProjectInfo();
					}
				}
				if (listSize == 0) {
					$scope.project_left_heigth = Math.ceil(Number(1/3)) * 186 - 6;
					for (var i=0; i<3; i++) {
						addProjectInfo();
					}
				}
			});*/
			
			function addProjectInfo() {
				var project = {
					"id" : "",
					"projectName" : "暂无",
					"schedule" : "000",
					"earthWork" : 0,
					"carTotal" : 0,
					"transportTimes" : 0,
					"startTime" : "暂无",
					"endTime" : "暂无"
				};
				$scope.projectList = $scope.projectList.concat(project);
			}
			
			/**============================================页面方法定义部分============================================**/
			$scope.detail = function(target){
				if("" == target.id) {
					return;
				}
				var aObj = $("#nav-tabs-project").children(":first").children(":first");
				aObj.click();
				$scope.dialog.title = "工程详情";
				projectId = target.id;
				if (projectId !== "" && projectId !== undefined) {
					IndexResource.projectDetail({"id": projectId}, function(response){
						$scope.dialog.data = response.projectInfo;
						$scope.dialog.list = response.projectTrip;
						vehicleIds = response.vehicleIds;
					});
				} else {
					$scope.dialog.data = {
						"name" : "暂无",
						"principal" : "暂无",
						"address" : "暂无",
						"telephone" : "暂无"
					}
					$scope.dialog.list = {}
				}
				$scope.dialog.show();
			};
			
			var subscribe = function(){
				$rootScope.stompClient.subscribe('/topic/status', function(response) {
					$scope.$apply(function(){
						var vector = JSON.parse(response.body);
						if (vehicleIds !== undefined) {
							var vehicleArrayString = vehicleIds.toString();
							if (vehicleArrayString.indexOf(vector.id)) {
								vector.info.state = null;
								map.resetVector(vector);
							}
						}
					});
				});
			};
			
			var vehicleOnOff = function(){
				$rootScope.stompClient.subscribe('/topic/onoff', function(response) {
					$scope.$apply(function(){
						var onOffInfo = JSON.parse(response.body);
						if (onOffInfo.state.index == 1) {
							var vector = onOffInfo.vehicle;
							map.removeVector(vector);
						}
					});
				});
			};
			/**============================================对话框元素初始化部分============================================**/
			$scope.$on('dialog-completed', function(e, dialog, element){
				/*monitorMap = new BMap.Map("monitorMap",{enableMapClick:false});
				monitorMap.enableScrollWheelZoom();
				var opts = {anchor: BMAP_ANCHOR_TOP_RIGHT};
				monitorMap.addControl(new BMap.NavigationControl(opts));
				monitorMap.centerAndZoom(new BMap.Point(113.64964385, 34.75661006), 16);
				monitorMap.enableAutoResize();*/
				
				/*dialog.initMonitor = function(){
					monitorMap.clearOverlays();
					subscribe();
					vehicleOnOff();
					if (projectId !== "" && projectId !== undefined) {
						IndexResource.monitorDetail({"id": projectId}, function(response){
							var beginStation = response.project.beginStation;
							var endStation = response.project.endStation;
							
							var outset = new BMap.Station(beginStation, outsetStyle);
							monitorMap.addOverlay(outset);
							var destination = new BMap.Station(endStation, destinationStyle);
							monitorMap.addOverlay(destination);
							
							angular.forEach($scope.dialog.data.projectRoutes, function(route){
								if (route.route != undefined) {
									var routerLine = BMap.RouterLine(route.route, routeStyle);
									routerLine && monitorMap.addOverlay(routerLine);
								}
							});
						});
					}
				};*/
			});
		}]);
	});
})(window.angular);