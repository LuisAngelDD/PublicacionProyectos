const Status = require('../modelos/statusPr');
const getAllStatus= async (req,res) =>{
    try {
        const st = await Status.find({});
        res.status(200).send(st)
    } catch (error) {
        res.status(500).send({message:"error"})
    }
}
module.exports = {
    getAllStatus
}