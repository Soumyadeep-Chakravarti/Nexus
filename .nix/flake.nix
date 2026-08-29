{
  description = "Nexus development environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs =
    {
      nixpkgs,
      flake-utils,
      ...
    }:
    flake-utils.lib.eachDefaultSystem (
      system:
      let
        pkgs = import nixpkgs { inherit system; };
      in
      {
        devShells.default = pkgs.mkShellNoCC {
          packages = [
            pkgs.jdk21
            pkgs.gradle
            pkgs.direnv
            pkgs.pre-commit
            pkgs.git
            pkgs.nixfmt
            pkgs.deadnix
            pkgs.statix
            pkgs.shellcheck
            pkgs.shfmt
            pkgs.yamllint
            pkgs.actionlint
          ];
        };
      }
    );
}
