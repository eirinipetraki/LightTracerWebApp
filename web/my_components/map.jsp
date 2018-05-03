<%@page import="java.text.Format"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="project.Commands"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
  <head>
    <% Float Latitude[] = (Float[])request.getAttribute(Commands.attrLatitudeData); %>
    <% Float Longtitude[] = (Float[])request.getAttribute(Commands.attrLongtitudeData); %>
   
     <% Timestamp[] Map_Time=(Timestamp[]) request.getAttribute(Commands.attrDateTimeMap);%>
   
    

  <title> GPS </title>
  <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
  </head>
 <body>
    
  <div id="HomeDiVContainer" >
      
    </div>

  <script type="text/javascript">
  var markersArray=[];
  var map;
  var marker;
  var infowindow = new google.maps.InfoWindow();
  var loc=[];
  var time=[];
  var tmp_count=0;
  var poly; 
  var address=[];
  
  <% for(int i=0;i<Latitude.length;i++){%>
      loc.push(new google.maps.LatLng(<%=Latitude[i]%>,<%=Longtitude[i]%>));
      time.push(new Date(<%=Map_Time[i].getTime()%>));
    
 <% }%>
  
  //Create a map with zoom factor, center location and map type
  map = new google.maps.Map(document.getElementById('HomeDiVContainer'), {
	zoom: 13,
	center: new google.maps.LatLng(<%=Latitude[0]%>,<%=Longtitude[0]%>),
	mapTypeId: google.maps.MapTypeId.ROADMAP
  });
  
 // Create a DIV to hold the control and call HomeControl()
  var homeControlDiv = document.createElement('div');
  var homeControl = new HomeControl(homeControlDiv, map);
  map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);
  
  var gc = new google.maps.Geocoder();
   var polyOptions = {
    strokeColor: '#000000',
    strokeOpacity: 1.0,
    strokeWeight: 3
  }
  poly = new google.maps.Polyline(polyOptions);
  poly.setMap(map);
  show();

  /*********************** geocode*****************/
  //Function creates a marker for given address and places it on the map
  function placeAddressOnMap(latlng,time,hours,minutes,seconds) {
    
  gc.geocode({'latLng': latlng}, function (res, status){
        if (status == google.maps.GeocoderStatus.OK) {
            if (res[3]) {
              marker = new google.maps.Marker({
                  position: latlng,
                  map: map,
                  title:time.toString(),
                  html:res[0].formatted_address.toString()+"<br>"+time+"<br> for "+hours+" hours "+minutes+" minites "+seconds+" seconds "
              });
              address.push(res[0].formatted_address.toString());
             markersArray.push(marker);
               
           
             
              google.maps.event.addListener(marker, 'click', function() {
                 infowindow.setContent(this.html);
                 infowindow.open(map,this);
                  
                         });
      
            } else {
              alert('No results found');
            }
          } else {
                setTimeout(function() { placeAddressOnMap(latlng,time,hours,minutes,seconds); }, (10));
             
                 }
           
  });
  }
  
function show() {

   for (i in markersArray) {
      markersArray[i].setMap(null);
    }
    var tmp=time[tmp_count];
    for(var j=tmp_count; j< loc.length; j++){
         
            var d1=time[j];
            if((d1.getDate()-tmp.getDate())!=0){
                   tmp_count=j;
                   break;
                    }
           else{
                    var d2=time[j+1];
                    var date1_ms = d1.getTime();
                    var date2_ms = d2.getTime();

                    // Calculate the difference in milliseconds
                    var difference_ms = date2_ms - date1_ms;
                    //take out milliseconds
                    difference_ms = difference_ms/1000;
                    var seconds = Math.floor(difference_ms % 60);
                    difference_ms = difference_ms/60; 
                    var minutes = Math.floor(difference_ms % 60);
                    difference_ms = difference_ms/60; 
                    var hours = Math.floor(difference_ms % 24);  
                    var days = Math.floor(difference_ms/24);
                       
                  
                     placeAddressOnMap(loc[j],time[j],hours,minutes,seconds);
                
               }
         
        
    } 
      flightPath.setMap(map);
}

function HomeControl(controlDiv, map) {

  // Set CSS styles for the DIV containing the control
  // Setting padding to 5 px will offset the control
  // from the edge of the map.
  controlDiv.style.padding = '5px';

  // Set CSS for the control border.
  var controlUI = document.createElement('div');
  controlUI.style.backgroundColor = 'white';
  controlUI.style.borderStyle = 'solid';
  controlUI.style.borderWidth = '2px';
  controlUI.style.cursor = 'pointer';
  controlUI.style.textAlign = 'center';
  controlUI.title = 'Click to show next day';
  controlDiv.appendChild(controlUI);

  // Set CSS for the control interior.
  var controlText = document.createElement('div');
  controlText.style.fontFamily = 'Arial,sans-serif';
  controlText.style.fontSize = '12px';
  controlText.style.paddingLeft = '4px';
  controlText.style.paddingRight = '4px';
  controlText.innerHTML = '<strong>Next day results</strong>';
  controlUI.appendChild(controlText);

  // Setup the click event listeners
  google.maps.event.addDomListener(controlUI, 'click', function() {
    show();
  });
}

  </script>
</body>
</html>

