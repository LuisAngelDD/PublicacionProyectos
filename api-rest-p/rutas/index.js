const express = require('express');
const router = express.Router();
const fs = require('fs');
const PATH_ROUTES = __dirname;
const removerExtenciones = (fileName)=>{
    return fileName.split('.').shift();
}
fs.readdirSync(PATH_ROUTES).filter((file)=>{
    const name = removerExtenciones(file);
    if (name !== 'index'){
        console.log(`cargando router: ${name}`)
        //router.use(`/${name}`);
    }
    router.use(`/${name}`)
})
//console.log({a})
module.exports = router;