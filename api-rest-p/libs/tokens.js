const sigJwt = require('jose');
const {encode} = require('./encode');
const {SECRET_TOKENT,time_0,time_1} = require('./config');
const createToken = async (req,res,next) =>{
    const emailOtp = req.authOTPCheck
    const email = req.signIn
    let val = ""
    var otp = ""
    if (email) {
        val = email
    }
    if (emailOtp){
        val = emailOtp
    }
    if (!email && !emailOtp){ 
        otp = `${Math.floor(100000+Math.random()*900000)}`
        val = await encode(otp)
    }
    try{
        const jwtConstructor = new sigJwt.SignJWT({ val });
        const encoder = new TextEncoder();
        if (email || emailOtp) {
            const jwt = await jwtConstructor
            .setProtectedHeader({ alg: "HS256", typ: "JWT" })
            .setIssuedAt()
            .setExpirationTime(time_0)
            .sign(encoder.encode(SECRET_TOKENT));
            res.status(200).send({jwt})
        }
        if (!email && !emailOtp){
            const jwt = await jwtConstructor
            .setProtectedHeader({ alg: "HS256", typ: "JWT" })
            .setIssuedAt()
            .setExpirationTime(time_1)
            .sign(encoder.encode(SECRET_TOKENT));
            res.status(200).send({jwt})
            req.createToken = otp
            next()
        }
    }catch(error){
        console.log(error)
        res.status(400).send("Ha ocurrido un error")
    }
}
const checkToken = async (req,res,next) =>{
    try {
        const { authorization } = req.headers;
        if (!authorization ) return res.status(401).send(false);
        const encoder = new TextEncoder();
        const {payload} = await sigJwt.jwtVerify(
            authorization,
            encoder.encode(SECRET_TOKENT)
        );
        req.checkToken = payload.val
        next()
    } catch (error) {
        return res.status(400).send("El token ha expirado")
    }
}
module.exports = {createToken,checkToken}