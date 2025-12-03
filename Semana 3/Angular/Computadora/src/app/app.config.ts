import { ApplicationConfig, importProvidersFrom, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormControl, FormsModule } from '@angular/forms';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(), //Proporciona un manejador global de errores para la aplicación 
    provideRouter(routes), //Configura el enrutador con las rutas definidas en app.routes.ts
    provideHttpClient(withInterceptorsFromDi()), //Importa el módulo HttpClient para hacer solicitudes HTTP
    importProvidersFrom(FormsModule) //Importa el módulo FormsModule para trabajar con formularios y enlaces de datos
  ]
};
