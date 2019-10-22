

$(function () {
   try {
      initIframe();
   }catch (e) {
      console.log(e)
   }
   // 回车键退出全屏
   $(window).keydown(function(event) {
      if (event.keyCode === 27) {
         $('#content-main').removeClass('max');
         $('#ax_close_max').hide();
      }
   });

});

//-------------- Iframe处理 Start ---------------//

// 初始化 iframe
function initIframe() {
   setInterval(function () {
      var iframe = document.getElementById("frame_content");
      var bHeight = iframe.contentWindow.document.body.scrollHeight;
      var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
      iframe.height =  Math.max(bHeight, dHeight);
   },200);
}

//计算元素集合的总宽度
function calSumWidth(elements) {
   var width = 0;
   $(elements).each(function() {
      width += $(this).outerWidth(true);
   });
   return width;
}

// 激活指定选项卡
function setActiveTab(element) {
   if (!$(element).hasClass('active')) {
      var currentId = $(element).data('id');
      // 显示tab对应的内容区
      $('.fun_iframe').each(function() {
         if ($(this).data('id') === currentId) {
            $(this).show().siblings('.fun_iframe').hide();
         }
      });
      $(element).addClass('active').siblings('.menuTab').removeClass('active');
      scrollToTab(element);
   }
}

//滚动到指定选项卡
function scrollToTab(element) {
   var marginLeftVal = calSumWidth($(element).prevAll()),
       marginRightVal = calSumWidth($(element).nextAll());
   // 可视区域非tab宽度
   var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
   //可视区域tab宽度
   var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
   //实际滚动宽度
   var scrollVal = 0;
   if ($(".page-tabs-content").outerWidth() < visibleWidth) {
      scrollVal = 0;
   } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
      if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
         scrollVal = marginLeftVal;
         var tabElement = element;
         while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
            scrollVal -= $(tabElement).prev().outerWidth();
            tabElement = $(tabElement).prev();
         }
      }
   } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
      scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
   }
   $('.page-tabs-content').animate({
          marginLeft: 0 - scrollVal + 'px'
       },
       "fast");
}

//查看左侧隐藏的选项卡
function scrollTabLeft() {
   var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
   // 可视区域非tab宽度
   var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
   //可视区域tab宽度
   var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
   //实际滚动宽度
   var scrollVal = 0;
   if (($(".page-tabs-content").width()) < visibleWidth) {
      return false;
   } else {
      var tabElement = $(".menuTab:first")
          ,offsetVal = 0;
      while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { //找到离当前tab最近的元素
         offsetVal += $(tabElement).outerWidth(true);
         tabElement = $(tabElement).next();
      }
      offsetVal = 0;
      if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
         while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).prev();
         }
         scrollVal = calSumWidth($(tabElement).prevAll());
      }
   }
   $('.page-tabs-content').animate({
          marginLeft: 0 - scrollVal + 'px'
       },
       "fast");
}

//查看右侧隐藏的选项卡
function scrollTabRight() {
   var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
   // 可视区域非tab宽度
   var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
   //可视区域tab宽度
   var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
   //实际滚动宽度
   var scrollVal = 0;
   if ($(".page-tabs-content").width() < visibleWidth) {
      return false;
   } else {
      var tabElement = $(".menuTab:first");
      var offsetVal = 0;
      while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { //找到离当前tab最近的元素
         offsetVal += $(tabElement).outerWidth(true);
         tabElement = $(tabElement).next();
      }
      offsetVal = 0;
      while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
         offsetVal += $(tabElement).outerWidth(true);
         tabElement = $(tabElement).next();
      }
      scrollVal = calSumWidth($(tabElement).prevAll());
      if (scrollVal > 0) {
         $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
             },
             "fast");
      }
   }
}

//通过遍历给菜单项加上data-index属性
$(".menuItem").each(function(index) {
   if (!$(this).attr('data-index')) {
      $(this).attr('data-index', index);
   }
});

// 右键菜单实现
$.contextMenu({
   selector: ".menuTab",
   trigger: 'right',
   autoHide: true,
   items: {
      "close_current": {
         name: "关闭当前",
         icon: "fa-close",
         callback: function(key, opt) {
            opt.$trigger.find('i').trigger("click");
         }
      },
      "close_other": {
         name: "关闭其他",
         icon: "fa-window-close-o",
         callback: function(key, opt) {
            setActiveTab(this);
            tabCloseOther();
         }
      },
      "close_left": {
         name: "关闭左侧",
         icon: "fa-reply",
         callback: function(key, opt) {
            setActiveTab(this);
            this.prevAll('.menuTab').not(":last").each(function() {
               if ($(this).hasClass('active')) {
                  setActiveTab(this);
               }
               $('.fun_iframe[data-id="' + $(this).data('id') + '"]').remove();
               $(this).remove();
            });
            $('.page-tabs-content').css("margin-left", "0");
         }
      },
      "close_right": {
         name: "关闭右侧",
         icon: "fa-share",
         callback: function(key, opt) {
            setActiveTab(this);
            this.nextAll('.menuTab').each(function() {
               $('.menuTab[data-id="' + $(this).data('id') + '"]').remove();
               $(this).remove();
            });
         }
      },
      "close_all": {
         name: "全部关闭",
         icon: "fa-window-close",
         callback: function(key, opt) {
            tabCloseAll();
         }
      },
      "step": "---------",
      "full": {
         name: "全屏显示",
         icon: "fa-arrows-alt",
         callback: function(key, opt) {
            setActiveTab(this);
            var target = $('.fun_iframe[data-id="' + this.data('id') + '"]');
            target.fullScreen(true);
         }
      },
      "refresh": {
         name: "刷新页面",
         icon: "fa-refresh",
         callback: function(key, opt) {
            setActiveTab(this);
            // var target = $('.fun_iframe[data-id="' + this.data('id') + '"]');
            // var url = target.attr('src');
            // target.attr('src', url).ready();
            // $.modal.loading("数据加载中，请稍后...");
            // target.attr('src', url).load(function () {
            //    $.modal.closeLoading();
            // });
            refreshTab();
         }
      },
      "open": {
         name: "新窗口打开",
         icon: "fa-link",
         callback: function(key, opt) {
            var target = $('.fun_iframe[data-id="' + this.data('id') + '"]');
            window.open(target.attr('src'));
         }
      }
   }
});

// 左移按扭
$('.tabLeft').on('click', scrollTabLeft);

// 右移按扭
$('.tabRight').on('click', scrollTabRight);

// 关闭当前选项卡
function tabCloseCurrent() {
   $('.page-tabs-content').find('.active i').trigger("click");
}

//关闭其他选项卡
function tabCloseOther() {
   $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function() {
      $('.fun_iframe[data-id="' + $(this).data('id') + '"]').remove();
      $(this).remove();
   });
   $('.page-tabs-content').css("margin-left", "0");
}

// 关闭全部选项卡
function tabCloseAll() {
   $('.page-tabs-content').children("[data-id]").not(":first").each(function() {
      $('.fun_iframe[data-id="' + $(this).data('id') + '"]').remove();
      $(this).remove();
   });
   $('.page-tabs-content').children("[data-id]:first").each(function() {
      $('.fun_iframe[data-id="' + $(this).data('id') + '"]').show();
      $(this).addClass("active");
   });
   $('.page-tabs-content').css("margin-left", "0");
}

// 点击全屏按钮全屏显示
$('#fullScreen').on('click', function () {
   $(document).toggleFullScreen();
});
// 双击选项卡全屏显示
$('.menuTabs').on('dblclick', '.menuTab', activeTabMax);

// 刷新按钮
$('.tabReload').on('click', refreshTab);

// 刷新iframe
function refreshTab() {
   var currentId = $('.page-tabs-content').find('.active').attr('data-id');
   var target = $('.fun_iframe[data-id="' + currentId + '"]');
   var url = target.attr('src');
   target.attr('src', url).ready();
}

// 点击激活选项卡菜单
$('.menuTabs').on('click', '.menuTab', activeTab);

// 点击选项卡菜单
function activeTab() {
   if (!$(this).hasClass('active')) {
      var currentId = $(this).data('id');
      // 显示tab对应的内容区
      $('.mainContent .fun_iframe').each(function() {
         if ($(this).data('id') === currentId) {
            $(this).show().siblings('.fun_iframe').hide();
            return false;
         }
      });
      $(this).addClass('active').siblings('.menuTab').removeClass('active');
      scrollToTab(this);
   }
}

// 双击选项卡全屏显示
function activeTabMax() {
   $('#content-main').toggleClass('max');
   $('#ax_close_max').show();
}

// 关闭全屏
$('#ax_close_max').click(function(){
   $('#content-main').toggleClass('max');
   $('#ax_close_max').hide();
})

//滚动到已激活的选项卡
function showActiveTab() {
   scrollToTab($('.menuTab.active'));
}
$('.tabShowActive').on('click', showActiveTab);



//-------------- Iframe处理 !END  ---------------//
