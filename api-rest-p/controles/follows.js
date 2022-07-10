const Proyectos = require('../modelos/proyectos')
const Follows = require('../modelos/follows');
const addRemoveFollow = async (req,res)=>{
    const {code} = req.params
    const user = req.checkToken
    if (await Follows.findOne({codeProyecto:code,follows:user})){
        const follow = await Follows.findOneAndUpdate({codeProyecto:code},{$pull:{follows:user}},{new:true});
        await Follows.findOneAndUpdate({codeProyecto:code},{count:follow.follows.length},{new:true});
        res.status(201).send({val:false,message:follow.follows.length})
    }else{
        const follow = await Follows.findOneAndUpdate({codeProyecto:code},{$addToSet:{follows:user}},{new:true});
        await Follows.findOneAndUpdate({codeProyecto:code},{count:follow.follows.length},{new:true});
        res.status(201).send({val:true,message:follow.follows.length})
    }
}
const getFollows = async (req,res)=>{
    const {code} = req.params
    const follow = await Follows.findOne({codeProyecto:code},{_id:0,count:1})
    res.status(200).send({val:true,message:follow.count})
}
const getSFollows = async(req,res) =>{
    const {code} = req.params
    const data = code.split(',')
    if (data.length >= 0){
        let follows = []
        for (i = 0; i<data.length;i++) {
            let count = await Follows.findOne({codeProyecto:data[i]},{_id:0,codeProyecto:1,count:1})
            if (count) follows.push(count)
        }
        res.status(200).send(follows)
    }else{
        res.status(404).send("No hay likes >:,u")
    }
}
const myFollows = async (req,res)=>{    
    const user = req.checkToken
    const follows = await Follows.find({follows:user},{_id:0,codeProyecto:1});
    if (follows.length>0){
        let data = []
        let busqueda = null
        if (req.params.status === "All" && req.params.type === "All" ){
            for (i = 0; i<follows.length;i++) {
                busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                if (busqueda) {data.push(busqueda)}
            }
        } else if (req.params.status !== "All" || req.params.type !== "All") {
            if (req.params.status === "All" && req.params.type !== "All"){
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.type},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) {data.push(busqueda)}
                }
            } else if (req.params.type === "All" && req.params.status !== "All") {
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.status},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) {data.push(busqueda)}
                }
            } else {
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.status,tipoProyecto:req.params.type},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) {data.push(busqueda)}
                }
            }        
        }
        if (data.length>0){
            res.status(200).send(data)
        } else {
            res.status(404).send("No se encontro ningun proyecto")
        }
    }else{
        res.status(404).send("No sigues ningun proyecto")
    }
}
const myFollowsName = async (req,res)=>{    
    const user = req.checkToken
    const follows = await Follows.find({follows:user},{_id:0,codeProyecto:1});
    const nombre = new RegExp(req.params.nombre, 'i');
    if (follows.length>0){
        let data = []
        let busqueda = null
        if (req.params.status === "All" && req.params.type === "All" ){
            for (i = 0; i<follows.length;i++) {
                busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,nombreProyecto:nombre},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                if (busqueda) data.push(busqueda)
            }
        } else if (req.params.status !== "All" || req.params.type !== "All") {
            if (req.params.status === "All" && req.params.type !== "All"){
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.type,nombreProyecto:nombre},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) data.push(busqueda)
                }
            } else if (req.params.type === "All" && req.params.status !== "All") {
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.status,nombreProyecto:nombre},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) data.push(busqueda)
                }
            } else {
                for (i = 0; i<follows.length;i++) {
                    busqueda = await Proyectos.findOne({_id:follows[i].codeProyecto,tipoProyecto:req.params.status,tipoProyecto:req.params.type,nombreProyecto:nombre},{nombreProyecto:1,statusProyecto:1,tipoProyecto:1,authorNameProyecto:1,descripcionProyecto:1})
                    if (busqueda) data.push(busqueda)
                }
            }        
        }
        if (data.length>0){
            res.status(200).send(data)
        } else {
            res.status(404).send("No se encontro ningun proyecto")
        }
    }else{
        res.status(404).send("No sigues ningun proyecto")
    }
}
const ifFollow = async (req,res)=>{
    const {code} = req.params
    const user = req.checkToken
    if (await Follows.findOne({codeProyecto:code,follows:user})){
        res.status(200).send({val:true,message:"lo sigues"})
    }else{
        res.status(404).send("no lo sigues")
    }
}
module.exports = {
    addRemoveFollow,myFollows,getFollows,ifFollow,getSFollows,myFollowsName
}