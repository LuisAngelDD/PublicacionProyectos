const Like = require('../modelos/likes');
const addRemoveLike = async (req,res)=>{
    const {code} = req.params
    const user = req.checkToken
    if (await Like.findOne({codeProyecto:code,likes:user})){
        const likesm = await Like.findOneAndUpdate({codeProyecto:code},{$pull:{likes:user}},{new:true});
        await Like.findOneAndUpdate({codeProyecto:code},{count:likesm.likes.length},{new:true});
        res.status(201).send({val:false,message:likesm.likes.length})
    }else{
        const likesm = await Like.findOneAndUpdate({codeProyecto:code},{$addToSet:{likes:user}},{new:true});
        await Like.findOneAndUpdate({codeProyecto:code},{count:likesm.likes.length},{new:true});
        res.status(201).send({val:true,message:likesm.likes.length})
    }
}
const getLikes = async (req,res)=>{
    const {code} = req.params
    const likesm = await Like.findOne({codeProyecto:code},{_id:0,count:1})
    res.status(200).send({val:true,message:likesm.count})
}
const getSLikes = async(req,res) =>{
    const {code} = req.params
    const data = code.split(',')
    if (data.length >= 0){
        let likes = []
        for (i = 0; i<data.length;i++) {
            let count = await Like.findOne({codeProyecto:data[i]},{_id:0,codeProyecto:1,count:1})
            if (count) likes.push(count)
        }
        res.status(200).send(likes)
    }else{
        res.status(404).send("No hay likes >:,u")
    }
}
const ifLike = async (req,res)=>{
    const {code} = req.params
    const user = req.checkToken
    if (await Like.findOne({codeProyecto:code,likes:user})){
        res.status(200).send({val:true,message:"lo sigues"})
    }else{
        res.status(404).send("no lo sigues")
    }
}
module.exports = {
    addRemoveLike,getLikes,ifLike,getSLikes
}