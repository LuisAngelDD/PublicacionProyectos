
const {config} = require('dotenv')
config()
module.exports={
    port:process.env.PORT,
    db:process.env.DB_URI,
    SECRET_TOKENT: process.env.SECRET_TOKENT,
    email_from: process.env.EMAIL,
    pass_email:process.env.EMAILPASS,
    time_0:process.env.TIME_0,
    time_1:process.env.TIME_1,
    cloud_name:process.env.CLOUDNAME,
    cloud_api_key:process.env.CLOUDAPIKEY,
    cloud_api_secret:process.env.CLOUDAPISECRET
}