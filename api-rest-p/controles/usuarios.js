const Usuarios = require('../modelos/usuario');
const UsuariosData = require('../modelos/dataUser');
const archivos = require('../libs/cloudinary');
const {email_from} = require('../libs/config');
const {encode,decode} = require('../libs/encode');
const fs = require('fs-extra');
const {transporter} = require('../libs/mailler')
const getAllUsers = async (req,res) =>{
    const us = await Usuarios.find();
    res.status(200).send(us)
}
const getUser = async (req,res) =>{
    const correo = req.checkToken
    const us = await UsuariosData.findOne({correo:correo},{_id:0,createdAt:0,updatedAt:0})
    res.status(200).send(us)
}
const signUp = async (req,res) =>{
    const {correo,password} = req.body;
    const user = await Usuarios.findOne({correo:correo});
    if (user !== null){
        return res.status(401).send(`Error el usuario ya existe`)
    }else{
        const newUser = new Usuarios({
            correo:correo,
            password: await encode(password),
            auth: false,
        });
        const useradd = await newUser.save()
        if (useradd !== null){
            res.status(201).send({val:true,message:'Usuario registrado'})
        }else{
            res.status(404).send(`Error al registrar el Usuario`)
        }
    }
}
const signIn = async (req,res,next) =>{
    const { email, password } = req.body;
    if (!email || !password) return res.status(401).send("Los campos no debes estar vacios");
    const user = await Usuarios.findOne({correo:email});
    if (user !== null){
        const match = await decode(req.body.password,user.password);
        if (match){
           if (user.auth == false) {
                return res.status(403).send(email)
            } else {
                 req.signIn = email
                 next()
            }
        }else{
            return res.status(404).send("Error al ingresar el correo y/o contraseña");
        }
    }else{
        return res.status(404).send("Error al ingresar el correo y/o contraseña");
    }
}
const verificarData = async (req,res) =>{
    const data = req.checkToken
    const user = await Usuarios.findOne({correo:data});
    if (user){
        const userData = await UsuariosData.findOne({correo:data});
        if (userData){
            res.status(200).send({val:true,message:'Usuario verificado'})
        } else {
            res.status(404).send("Faltan datos")
        }
    }else{
        res.status(401).send("El usuario no existe")
    }
}
const cargarInfo = async (req,res) => {
    const data = req.checkToken
    const user = await UsuariosData.findOne({correo:data});
    if (user){
        res.status(200).send({val:true,message:'Ya existen datos'})
    } else {
        const {userName,about,occupation} = req.body;
        const userData = new UsuariosData({
            correo:data,
            nombre:userName,
            about:about,
            ocupacion:occupation,
        });
        if(req.files?.image){
            console.log(req.files.image)
            const img = await archivos.uploadImg(req.files.image.tempFilePath);
            userData.public_id=img.public_id
            userData.secure_url=img.secure_url
            await fs.unlink(req.files.image.tempFilePath);
        }
        const useradd = await userData.save()
        if (useradd !== null){
            res.status(201).send({val:true,message:'Datos cargados'})
        }else{
            res.status(404).send("Hubo un error al cargar los datos")
        }
    }
}
const updateUser = async (req,res) =>{
    await Usuarios.findByIdAndUpdate(req.params.usuarioId,req.body,{new:true}).then(usUp => res.status(201).send({message:'Usuario actualizado',usUp})).catch(err => res.status(500).send({message:`Error al actualizar el Usuario ${err}`}));
}
const deleteUser = async (req,res) =>{
    const sup = await Usuarios.findByIdAndRemove(req.params.usuarioId);
    if (!sup){
        return res.status(404).send({message:"Error pa"});
    }
    console.log(sup.image.public_id)
    await archivos.deletImg(sup.image.public_id);
    res.status(200).send({message:"usuario eliminado"});
}
const authOTPCreate = async (req,res,next) =>{
    const {email} = req.body;
    const otp = req.createToken
    try{
        await transporter.sendMail({
            from: `Nombre app" <${email_from}>`, // sender address
            to: email, // list of receivers
            html: `<p>Ingrese el siguiente codigo ${otp} para verificar su cuenta.</p>`, // html body
        });
    }catch(err){
        console.log(err)
    }
}
const authOTPCheck = async (req,res,next) =>{
    const opt = req.checkToken
    const match = await decode(req.body.optSend,opt);
    if (match){
        await Usuarios.findOneAndUpdate({correo:req.body.email},{auth:true});
        req.authOTPCheck = req.body.email
        next()
    }else{
        res.status(401).send("Codigo incorrecto");
    }
}
module.exports = {
    signUp,
    signIn,
    authOTPCreate,
    authOTPCheck,
    verificarData,
    cargarInfo,
    getAllUsers,
    getUser,
    updateUser,
    deleteUser
}