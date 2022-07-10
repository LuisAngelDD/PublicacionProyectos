
const {config} = require('dotenv')
config()
module.exports={
    port:process.env.PORT,
    db:process.env.DB_URI,
}