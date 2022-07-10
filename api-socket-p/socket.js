const Proyectos = require('./modelos/proyectos')
const Follows = require('./modelos/follows')
const Likes = require('./modelos/likes')
const conexion = (io) => {
  io.on("connection", (socket) => {
    console.log("nuevo socket connectado:", socket.id);
    socket.on('client:follows:update',async(code)=>{
      const follows = await Follows.findOne({codeProyecto:code},{_id:0,codeProyecto:1,count:1});     
      io.emit('server:myFollows:update', follows);
    })
    socket.on('client:likes:update',async(code)=>{
      const likes = await Likes.findOne({codeProyecto:code},{_id:0,codeProyecto:1,count:1});     
      io.emit('server:likes:update', likes);
    })
    socket.on('client:proyectos:update',async(code)=>{
      const pr = await Proyectos.findById(code,{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1});     
      io.emit('server:likes:update', pr);
    })
    socket.on('client:new:proyectos',async()=>{
      io.emit('server:new:proyectos', "Actualiza para mas contenido");
    })
    socket.on('client:update:proyectos',async()=>{
      io.emit('server:update:proyectos', "Actualiza para mas contenido");
    })
    socket.on("disconnect", () => {
      console.log("Socket: "+socket.id, "desconectado");
    });
  })
}
module.exports={conexion}