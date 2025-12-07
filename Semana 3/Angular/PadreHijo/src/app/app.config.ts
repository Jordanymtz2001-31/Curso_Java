import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), // Rutas de la aplicacion
    importProvidersFrom(FormsModule), // Importacion de FormsModule para formularios
    provideHttpClient(withInterceptorsFromDi()) // Importacion de HttpClient para peticiones HTTP
                                              //El withInterceptorsFromDi() permite usar interceptores
  ] 
};
