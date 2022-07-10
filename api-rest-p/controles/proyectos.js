const Proyectos = require('../modelos/proyectos')
const Likes = require('../modelos/likes')
const UsuariosData = require('../modelos/dataUser')
const Follows = require('../modelos/follows')
const getAllProyects = async (req,res) =>{
    let proyectos = null
    if (req.params.status === "All" && req.params.type === "All" ){
        proyectos = await Proyectos.find({},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
    } else if (req.params.status !== "All" || req.params.type !== "All") {
        if (req.params.status === "All" && req.params.type !== "All"){
            proyectos = await Proyectos.find({tipoProyecto:req.params.type},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
        } else if (req.params.type === "All" && req.params.status !== "All") {
            proyectos = await Proyectos.find({statusProyecto:req.params.status},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        } else {
            proyectos = await Proyectos.find({statusProyecto:req.params.status,tipoProyecto:req.params.type},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        }        
    }
    if (proyectos.length>0){
        res.status(200).send(proyectos)
    }else{
        res.status(404).send("Error")
    }
}
const getAllProyectNombre = async (req,res) =>{
    let proyectos = null
    const nombre = new RegExp(req.params.nombre, 'i');
    if (req.params.status === "All" && req.params.type === "All" ){
        proyectos = await Proyectos.find({nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
    } else if (req.params.status !== "All" || req.params.type !== "All") {
        if (req.params.status === "All" && req.params.type !== "All"){
            proyectos = await Proyectos.find({tipoProyecto:req.params.type,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
        } else if (req.params.type === "All" && req.params.status !== "All") {
            proyectos = await Proyectos.find({statusProyecto:req.params.status,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        } else {
            proyectos = await Proyectos.find({statusProyecto:req.params.status,tipoProyecto:req.params.type,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        }        
    }
    if (proyectos.length>0){
        res.status(200).send(proyectos)
    }else{
        res.status(404).send("Error")
    }
}
const getProyect = async (req,res) =>{
    console.log(req.params.code)
    const proyecto = await Proyectos.findById(req.params.code,{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
    if (!proyecto){
        res.status(404).send("Error")
    }else{
        res.status(200).send(proyecto)
    }
}
const myProyects = async (req,res)=>{
    const user = req.checkToken
    let proyectos = null
    if (req.params.status === "All" && req.params.type === "All" ){
        proyectos = await Proyectos.find({authorEmailProyecto:user},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
    } else if (req.params.status !== "All" || req.params.type !== "All") {
        if (req.params.status === "All" && req.params.type !== "All"){
            proyectos = await Proyectos.find({authorEmailProyecto:user,tipoProyecto:req.params.type},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
        } else if (req.params.type === "All" && req.params.status !== "All") {
            proyectos = await Proyectos.find({authorEmailProyecto:user,statusProyecto:req.params.status},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        } else {
            proyectos = await Proyectos.find({authorEmailProyecto:user,statusProyecto:req.params.status,tipoProyecto:req.params.type},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        }        
    }
    if (proyectos.length>0){
        res.status(200).send(proyectos)
    }else{
        res.status(404).send("Error")
    }
}
const myProyectsNombre = async (req,res)=>{
    const user = req.checkToken
    let proyectos = null
    const nombre = new RegExp(req.params.nombre, 'i');
    if (req.params.status === "All" && req.params.type === "All" ){
        proyectos = await Proyectos.find({authorEmailProyecto:user,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
    } else if (req.params.status !== "All" || req.params.type !== "All") {
        if (req.params.status === "All" && req.params.type !== "All"){
            proyectos = await Proyectos.find({authorEmailProyecto:user,tipoProyecto:req.params.type,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
        } else if (req.params.type === "All" && req.params.status !== "All") {
            proyectos = await Proyectos.find({authorEmailProyecto:user,statusProyecto:req.params.status,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        } else {
            proyectos = await Proyectos.find({authorEmailProyecto:user,statusProyecto:req.params.status,tipoProyecto:req.params.type,nombreProyecto:nombre},{_id:1,nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1}) 
        }        
    }
    if (proyectos.length>0){
        res.status(200).send(proyectos)
    }else{
        res.status(404).send("Error")
    }
}
const postProyect = async (req,res) =>{
    const author = req.checkToken
    const {nombre,descripcion,type} = req.body 
    let saveProyect = null
    const user = await UsuariosData.findOne({correo:author},{_id:0,nombre:1}) 
    if (!user) return res.status(404).send("El usuario no se encontro") 
    const newProyect = new Proyectos({
        nombreProyecto:nombre,
        descripcionProyecto:descripcion,
        tipoProyecto:type,
        authorEmailProyecto:author,
        authorNameProyecto:user.nombre,
        statusProyecto:"Proyecto Activo",
    })
    try{
        saveProyect = await newProyect.save()
    }catch(error){
        console.log(error)
        return res.status(400).send("Error al publicar el proyecto")
    }
    try{
        const newFollow = new Follows({
            codeProyecto:saveProyect._id,
        }) 
        await newFollow.save()
    }catch(errorF){
        console.log(errorF)
        await Proyectos.findOneAndDelete({codeProyecto:saveProyect._id})
        return res.status(400).send("Error al publicar el proyecto") 
    }
    try{
        const newLikes = new Likes({
            codeProyecto:saveProyect._id,
        }) 
        await newLikes.save() 
    }catch(errorL){
        console.log(errorL)
        await Follows.findOneAndDelete({codeProyecto:saveProyect._id})
        await Proyectos.findOneAndDelete({codeProyecto:saveProyect._id})
        return res.status(400).send("Error al publicar el proyecto") 
    }
    res.status(201).send({val:true,message:"Proyecto cargado"})
}
const updateProyect = async (req,res) =>{
    await Proyectos.findByIdAndUpdate(req.params.proyectoId,req.body).then(prUp => res.status(201).send({val:true,message:"Proyecto cargado"})).catch(err => res.status(500).send("Error")) 
}
const deleteProyect = async (req,res) =>{
    await Proyectos.findByIdAndRemove(req.params.proyectoId).then(proyect=>res.status(200).send({message:`Proyecto eliminado exitosamente`})).catch(fail => res.status(400).send({message:"Error"})) 
}
module.exports = {
    getAllProyects,
    getAllProyectNombre,
    postProyect,
    getProyect,
    myProyects,
    myProyectsNombre,
    updateProyect
}