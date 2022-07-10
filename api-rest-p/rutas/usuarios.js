const express = require('express');
const expRt = express.Router();
const ctrlUsers = require('../controles/usuarios');
const fileUpload = require('express-fileupload')
const {createToken,checkToken} = require("../libs/tokens")
expRt.post('/usuario/info',checkToken,ctrlUsers.getUser);
expRt.post('/usuario/signUp',ctrlUsers.signUp);
expRt.post('/usuario/signIn',ctrlUsers.signIn,createToken);
expRt.post('/usuario/authCreate',createToken,ctrlUsers.authOTPCreate);
expRt.post('/usuario/authCheck',checkToken,ctrlUsers.authOTPCheck,createToken);
expRt.post('/usuario/checkData',checkToken,ctrlUsers.verificarData);
expRt.post('/usuario/uploadUserData',checkToken,fileUpload({useTempFiles : true,tempFileDir : './tmp/'}), ctrlUsers.cargarInfo);
expRt.get('/usuario/', ctrlUsers.getAllUsers);
expRt.get('/usuario/:usuarioId', ctrlUsers.getUser);
expRt.put('/usuario/:usuarioId',ctrlUsers.updateUser);
expRt.delete('/usuario/:usuarioId', ctrlUsers.deleteUser);

module.exports = expRt;