import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { ListarPadre } from './Componente/Padre/listar-padre/listar-padre';
import { GuardarPadre } from './Componente/Padre/guardar-padre/guardar-padre';
import { EditarPadre } from './Componente/Padre/editar-padre/editar-padre';
import { ListarHijo } from './Componente/Hijo/listar-hijo/listar-hijo';
import { GuardarHijo } from './Componente/Hijo/guardar-hijo/guardar-hijo';
import { EditarHijo } from './Componente/Hijo/editar-hijo/editar-hijo';

export const routes: Routes = [
    
    {
        path: 'listaPadres',
        component: ListarPadre
    },
    {
        path: 'guardarPadre',
        component: GuardarPadre
    },
    {
        path: 'editarPadre',
        component: EditarPadre
    },
    {
        path: 'listaHijos',
        component: ListarHijo
    },
    {
        path: 'guardarHijo',
        component: GuardarHijo
    },
    {
        path: 'editarHijo',
        component: EditarHijo
    }
];
