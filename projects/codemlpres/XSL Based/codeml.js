var mnu = new Array();
function winOnLoad()
{
  mnu[0] = new BlockCollapse('pcode1');
 }
window.onunload = function(){
  return;
}
function BlockCollapse(parentElement, action){
  var container = document.getElementById(parentElement);
  if (!container) {return null;}
  var isUL = container.nodeName.toUpperCase() == 'UL';
  var i, target, aTgt = document.getElementsByTagName(isUL ? 'UL':'DIV', container);
  for (i = 0; i < aTgt.length; ++i) {
    target = PreviousElement(aTgt[i]);
    if (target && (isUL || target.nodeName.charAt(0).toUpperCase() == 'H')) {
      aTgt[i].style.display = action ? 'block' : 'block';
      target.style.cursor = 'default';
      target.TgtCode = aTgt[i];
      target.onclick = trg_onClick;
    }  
  }
  function trg_onClick()  {
    var tgt = this.TgtCode.style;
	var pu = this.parentNode;
	var spa=pu.firstChild.style;
    if (tgt.display == 'block') {
      tgt.display = 'none';
	  spa.backgroundImage='url(plus.gif)';
    }  
    else {
      tgt.display = 'block';
	  spa.backgroundImage='url(minus.gif)';
    }
  }
  this.onUnload = function()  {
    if (!container || !aTgt) {return;}
    for (i = 0; i < aTgt.length; ++i) {
      target = PreviousElement(aTgt[i]);
      if (target && (isUL || target.nodeName.charAt(0).toUpperCase == 'H')) {
        target.TgtCode = null;
        target.onclick = null;
      }
    }
  }
  function PreviousElement(element, tag){
    var s = element ? element.previousSibling : null;
    if (tag) while (s && s.nodeName != tag) { s = s.previousSibling; }
    else while (s && s.nodeType != 1) { s = s.previousSibling; }
    return s;
  }
}

