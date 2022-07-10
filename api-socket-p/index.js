const {app} = require('./app');
const {port,db} = require('./conf')
const {connect} = require('mongoose');
const {conexion} = require('./socket')
const {Server} = require('socket.io')
const http = require('http')
connect(db).then(db => console.log('BD esta conectada'),{
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
}).catch(error => console.log(error));
const server = http.createServer(app);
const httpServer = server.listen(port);
console.log("Server on http://localhost:", port);
const io = new Server(httpServer);
conexion(io)