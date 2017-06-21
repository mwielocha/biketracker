var $ = require("jquery");
var ol = require('openlayers');

function init() {

  $.get('/api/tracks', function(track) {
    display(tracks);
  });

}

function dysplay(tracks) {

  var raster = new ol.layer.Tile({
    source: new ol.source.OSM()
  });

  var layers = tracks.map(function(track) {
    new ol.layer.Vector({
      source: new ol.source.Vector({
        url: '/api/track/' + track,
        format: new ol.format.GPX()
      })
    });
  });
    
  new ol.Map({
    target: 'map',
    layers: layers.unshift(raster),
    view: new ol.View({
      center: [0, 0],
      zoom: 2
    })
  });    
}


init();
