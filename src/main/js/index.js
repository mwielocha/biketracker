var $ = require("jquery");
var ol = require('openlayers');

function init() {

  $.get('/api/tracks', function(tracks) {
    display(tracks);
  });

}

function display(tracks) {

  var raster = new ol.layer.Tile({
    source: new ol.source.OSM()
  });

  var style = {
    'Point': new ol.style.Style({
      image: new ol.style.Circle({
        fill: new ol.style.Fill({
          color: 'rgba(255,255,0,0.4)'
        }),
        radius: 5,
        stroke: new ol.style.Stroke({
          color: '#ff0',
          width: 1
        })
      })
    }),
    'LineString': new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: '#f00',
        width: 3
      })
    }),
    'MultiLineString': new ol.style.Style({
      stroke: new ol.style.Stroke({
        color: '#ff0000',
        width: 3
      })
    })
  };

  var layers = tracks.map(function(track) {
    
    return new ol.layer.Vector({
      source: new ol.source.Vector({
        url: '/api/track/' + track,
        format: new ol.format.GPX()
      }),
      style: function(feature) {
        return style[feature.getGeometry().getType()];
      }
    });
    
  });
    
  var map = new ol.Map({
    target: 'map',
    layers: [raster].concat(layers),
    view: new ol.View({
      center: [0, 0],
      zoom: 2
    })
  });

  map.getView().fit([
    864855.8589097988,
    5095952.99504686,
    3389718.3346956447,
    7691194.912666457
  ]);
  
  var select = new ol.interaction.Select();
  
  map.addInteraction(select);
  select.setActive(true);
 
}


init();
