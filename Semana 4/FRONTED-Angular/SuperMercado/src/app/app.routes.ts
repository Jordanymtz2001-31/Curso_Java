import { Routes } from '@angular/router';
import { ListarT } from './Componente/Tienda/listar-t/listar-t';
import { ListarP } from './Componente/Producto/listar-p/listar-p';
import { ListarC } from './Componente/Cliente/listar-c/listar-c';
import { ListarE } from './Componente/Empleado/listar-e/listar-e';
import { ListarD } from './Componente/Departamento/listar-d/listar-d';
import { ListarPR } from './Componente/Proveedor/listar-pr/listar-pr';
import { GuardarT } from './Componente/Tienda/guardar-t/guardar-t';
import { GuardarP } from './Componente/Producto/guardar-p/guardar-p';
import { GuardarC } from './Componente/Cliente/guardar-c/guardar-c';
import { GuardarE } from './Componente/Empleado/guardar-e/guardar-e';
import { GuardarD } from './Componente/Departamento/guardar-d/guardar-d';
import { GuardarPR } from './Componente/Proveedor/guardar-pr/guardar-pr';
import { EditarT } from './Componente/Tienda/editar-t/editar-t';
import { EditarP } from './Componente/Producto/editar-p/editar-p';
import { EditarC } from './Componente/Cliente/editar-c/editar-c';
import { EditarPR } from './Componente/Proveedor/editar-pr/editar-pr';
import { EditarD } from './Componente/Departamento/editar-d/editar-d';
import { EditarE } from './Componente/Empleado/editar-e/editar-e';
import { ListarDetallesT } from './Componente/Tienda/listar-detalles-t/listar-detalles-t';
import { DetalleProducto } from './Componente/Departamento/detalle-producto/detalle-producto';

export const routes: Routes = [

    //Aqui van las rutas de navegacion de la aplicacion que vienen de app.ts
    //Para unir las vistas con los metodos de navegacion

    //LISTAR--------------------------------------------------------------------------------------
    {path: 'listar-tiendas', component: ListarT},
    {path: 'listar-productos', component: ListarP},
    {path: 'listar-clientes', component: ListarC},
    {path: 'listar-empleados', component: ListarE},
    {path: 'listar-departamentos', component: ListarD},
    {path: 'listar-proveedores', component: ListarPR},

    //GUARDAR-------------------------------------------------------------------------------------
    {path: 'guardar-tiendas', component: GuardarT},
    {path: 'guardar-productos', component: GuardarP},
    {path: 'guardar-clientes', component: GuardarC},
    {path: 'guardar-empleados', component: GuardarE},
    {path: 'guardar-departamentos', component: GuardarD},
    {path: 'guardar-proveedores', component: GuardarPR},

    //EDITAR--------------------------------------------------------------------------------------
    {path: 'editar-tiendas/:id', component: EditarT},
    {path: 'editar-productos/:id', component: EditarP},
    {path: 'editar-clientes/:id', component: EditarC},
    {path: 'editar-empleados/:id', component: EditarE},
    {path: 'editar-departamentos/:id', component: EditarD},
    {path: 'editar-proveedores/:id', component: EditarPR},

    //DETALLES-----------------------------------------------------------------------------------
    {path: 'detalles-tiendas', component: ListarDetallesT},
    {path: 'detalles-depaProduc', component: DetalleProducto},
    {path: 'detalles-depaEmple', component: DetalleProducto},


];
