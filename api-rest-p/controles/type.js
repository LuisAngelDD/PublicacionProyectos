const Type = require('../modelos/tpo');
const getAllType= async (req,res) =>{
    try {
        const st = await Type.find({});
        res.status(200).send(st)
    } catch (error) {
        res.status(500).send({message:"error"})
    }
}
module.exports = {
    getAllType
}