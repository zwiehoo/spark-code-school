var net = require('net');
var wikichanges = require("wikichanges");

var server = net.createServer(function(c) { //'connection' listener
  var w = new wikichanges.WikiChanges({ircNickname: 'spark-code-school'});
  console.log('client connected');
  c.setNoDelay(true)
  c.on('end', function() {
    console.log('client disconnected');
    w.disconnect();
  }); 
  
  w.listen(function(change) {
    console.log(change.page + " " + change.pageUrl);
    c.write(JSON.stringify(change));
    c.write('\n')
  });

  
});
server.listen(8124, function() { //'listening' listener
  console.log('server bound');
});
