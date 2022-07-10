const sigJwt = require('jose');
const {SECRET_TOKENT} = require('../conf');
const checkToken = async (token) =>{
    try {
        if (!token ) return res.status(401).send(false);
        const encoder = new TextEncoder();
        const {payload} = await sigJwt.jwtVerify(
            token,
            encoder.encode(SECRET_TOKENT)
        );
        return payload.val
    } catch (error) {
    }
}
module.exports = {checkToken}