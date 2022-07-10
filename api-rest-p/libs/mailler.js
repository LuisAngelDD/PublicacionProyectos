const nodemailer = require("nodemailer");
const {pass_email, email_from} = require('../libs/config');
const transporter = nodemailer.createTransport({
    host: "smtp.gmail.com",
    port: 465,
    secure: true, // true for 465, false for other ports
    auth: {
      user: email_from, // generated ethereal user
      pass: pass_email, // generated ethereal password
    },
});
transporter.verify().then(()=>{
    console.log('Listo para el envio')
})
module.exports = {transporter}