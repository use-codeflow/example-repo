/**
 * Random numbers, 3 ways. Usage:
 * - random() : returns a random integer
 * - random(30) : returns a random integer from 0 to 30
 * - random(20, 50) : returns a random integer from 20 to 50
 */
var random = function() {
  var f = Math.floor, r = Math.random, a = arguments;
  switch(a.length){
    case 0: return f(r()); break;
    case 1: return f(r() * (a[0] + 1));break;
    case 2: return f(r() * (a[1] - a[0] + 1)) + a[0];break;
  }
};

